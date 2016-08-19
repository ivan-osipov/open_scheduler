package org.pyjjs.scheduler.core.api.impl.actors.common

import akka.actor.UntypedActor
import org.pyjjs.scheduler.core.api.impl.actors.Actor
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.InitEntityAgentMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class StateOrientedActor<T : ActorState> : UntypedActor() {

    protected var LOG: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var actorState: T

    override fun preStart() {
        super.preStart()
        actorState = createInitialState()
    }

    protected fun updateActorState(actorState: T) {
        this.actorState = actorState
    }

    protected val copyOfActorState: T
        get() = actorState.copySelf() as T

    private fun checkAbilityToHandle(someObject: Any?): Boolean {
        val actorState = copyOfActorState
        if (actorState.isInitialized && (someObject is Message)) return true
        if (someObject is InitEntityAgentMessage<*>) return true
        self.tell(someObject, sender)
        return false
    }

    @Throws(Exception::class)
    override fun onReceive(someObject: Any) {
        try {
            if (checkAbilityToHandle(someObject)) {
                onMessage(someObject as Message)
            }
        } catch(e: Exception) {
            LOG.error("Problem with processing", e)
        }
    }

    abstract fun onMessage(message: Message)

    protected abstract fun createInitialState(): T
}
