package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.InitEntityAgentMessage;
import org.pyjjs.scheduler.core.model.Task;

public class TaskInitMessage extends InitEntityAgentMessage<Task> {

    public TaskInitMessage(ActorRef sender, Task source) {
        super(sender, source);
    }
}
