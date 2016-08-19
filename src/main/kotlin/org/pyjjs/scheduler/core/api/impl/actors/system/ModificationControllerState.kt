package org.pyjjs.scheduler.core.api.impl.actors.system

import akka.actor.ActorContext
import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import scala.concurrent.Promise

class ModificationControllerState(actorContext: ActorContext,
                                  var taskSupervisor: ActorRef,
                                  var resourceSupervisor: ActorRef) : ActorState(actorContext) {

    override fun copySelf() = this
}
