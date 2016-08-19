package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours

import com.google.common.collect.Maps
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorStateInteraction
import org.pyjjs.scheduler.core.api.impl.actors.common.StateOrientedActor
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.InitEntityAgentMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message

abstract class BehaviourBasedActor<T : ActorState> : StateOrientedActor<T>() {

    private val behaviours = Maps.newHashMap<Class<out Message>, Behaviour<T, out Message>>()

    protected fun <B : Behaviour<T, out Message>> addBehaviour(behaviourClass: Class<B>) {
        val behaviour = behaviourClass.newInstance()
        addBehaviour(behaviour)
    }

    private fun addBehaviour(behaviour: Behaviour<T, out Message>) {
        checkNotNull(behaviour)
        behaviours.put(behaviour.processMessage(), behaviour)
    }

    @Throws(Exception::class)
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

    private fun isAppropriateBehaviour(messageType: Class<out Message>, someObject: Message)
            = messageType.isAssignableFrom(someObject.javaClass)
}
