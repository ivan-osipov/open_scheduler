package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState

abstract class TaskBehaviour<M : Message> : Behaviour<TaskActorState, M>()
