package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.messages.ResourcePriceInfoMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;

public class ResourceInfoStoreBehaviour extends Behaviour<TaskActorState, ResourcePriceInfoMessage> {
    @Override
    protected void perform(ResourcePriceInfoMessage message) {
        System.out.println(message.getSender() + " предлагает разместиться за " + message.getPlacingPrice() + "$");
    }

    @Override
    protected Class<ResourcePriceInfoMessage> processMessage() {
        return ResourcePriceInfoMessage.class;
    }

}
