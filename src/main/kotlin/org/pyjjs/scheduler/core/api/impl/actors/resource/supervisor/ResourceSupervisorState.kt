package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor

import akka.actor.ActorContext
import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.supervisor.SupervisorState
import org.pyjjs.scheduler.core.model.Resource

class ResourceSupervisorState(actorContext: ActorContext) : SupervisorState<Resource>(actorContext) {

    var taskSupervisor: ActorRef? = null

    override fun copySelf() = this
}
