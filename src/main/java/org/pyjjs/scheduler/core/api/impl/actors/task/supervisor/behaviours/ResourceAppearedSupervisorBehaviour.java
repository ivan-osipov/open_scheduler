package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours;

import akka.actor.ActorRef;
import com.google.common.collect.Lists;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages.ResourceAppearedMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActor;
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisorState;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourceAppearedSupervisorBehaviour extends Behaviour<TaskSupervisorState, ResourceAppearedMessage> {
    @Override
    protected void perform(ResourceAppearedMessage message) {
        TaskSupervisorState state = getActorState();
        List<TaskSupervisorState.TaskDiscontent> discontent = Lists.newArrayList(state.getTaskDiscontents());
        //firstly, task with max discontent
        Collections.sort(discontent, (o1, o2) -> {
            Double discontent1 = o1.getDiscontent();
            Double discontent2 = o2.getDiscontent();
            return Objects.compare(discontent2, discontent1, (d2, d1) -> ((d2 == null) ? 1 : (d1 == null ? 1 : d2.compareTo(d1))));
        });
        List<ActorRef> tasks = discontent.stream()
                .map(TaskSupervisorState.TaskDiscontent::getTaskActor)
                .collect(Collectors.toList());
        sendToAll(tasks, message);
    }

    @Override
    protected Class<ResourceAppearedMessage> processMessage() {
        return ResourceAppearedMessage.class;
    }
}
