package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;

public abstract class TaskBehaviour<M extends Message> extends Behaviour<TaskActorState, M> {
}
