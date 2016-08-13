package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages.ResourceInitMessage;

public class ResourceInitBehaviour extends Behaviour<ResourceActorState, ResourceInitMessage> {

    @Override
    protected void perform(ResourceInitMessage message) {
        ResourceActorState resourceActorState = getActorState();
        resourceActorState.setSource(message.getSource());
        resourceActorState.setInitialized(true);
        saveActorState(resourceActorState);
    }

    @Override
    protected Class<ResourceInitMessage> processMessage() {
        return ResourceInitMessage.class;
    }

}
