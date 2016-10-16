package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours

import com.google.common.collect.Maps
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorStateInteraction
import org.pyjjs.scheduler.core.api.impl.actors.common.StateOrientedActor
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import kotlin.reflect.KClass

abstract class BehaviourBasedActor<T : ActorState>() : StateOrientedActor<T>() {

    private val behaviours = Maps.newHashMap<KClass<out Message>, Behaviour<T, out Message>>()

    protected fun <B : Behaviour<T, out Message>> addBehaviour(behaviourClass: KClass<B>) {
        val behaviour = behaviourClass.java.newInstance()
        addBehaviour(behaviour)
    }

    private fun addBehaviour(behaviour: Behaviour<T, out Message>) {
        checkNotNull(behaviour)
        behaviours.put(behaviour.processMessage(), behaviour)
    }

    override fun preStart() {
        super.preStart()
        init()
    }

    protected abstract fun init()

    override fun onMessage(message: Message) {
        behaviours.entries
                .filter({ behaviourEntry -> isAppropriateBehaviour(behaviourEntry.key, message) })
                .forEach { behaviourEntry ->
                    val behaviour = behaviourEntry.value
                    behaviour.setActorStateInteraction(object : ActorStateInteraction<T> {
                        override fun getActorState(): T {
                            return copyOfActorState
                        }

                        override fun saveActorState(actorState: T) {
                            updateActorState(actorState)
                        }
                    })
                    behaviour.invoke(message)
                }
    }

    private fun isAppropriateBehaviour(messageType: KClass<out Message>, someObject: Message)
            = messageType.java.isAssignableFrom(someObject.javaClass)
}
