package org.pyjjs.scheduler.core.actors.task.supervisor.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;
import org.pyjjs.scheduler.core.actors.common.messages.InitEntityAgentMessage;
import org.pyjjs.scheduler.core.model.primary.Task;

public class TaskInitMessage extends InitEntityAgentMessage<Task> {

    public TaskInitMessage(ActorRef sender, Task source) {
        super(sender, source);
    }
}
