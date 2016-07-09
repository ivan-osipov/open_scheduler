package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.common.messages.Message;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;

public abstract class TaskBehaviour<M extends Message> extends Behaviour<TaskActorState, M> {
}
