package org.pyjjs.scheduler.core.actors.system;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.system.behaviours.DataSourceChangeBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModificationController extends BehaviourBasedActor<ModificationControllerState> {

    private static final Logger LOG = LoggerFactory.getLogger(ModificationController.class);


    public ModificationController(ActorRef taskSupervisor, ActorRef resourceSupervisor) {
        ModificationControllerState state = getCopyOfActorState();
        state.setTaskSupervisor(taskSupervisor);
        state.setResourceSupervisor(resourceSupervisor);
        updateActorState(state);
    }

    @Override
    protected void init() {
        fillBehaviours();
        LOG.info("Modification controller is initialized");
    }

    private void fillBehaviours() {
        addBehaviour(DataSourceChangeBehaviour.class);
    }

    @Override
    protected ModificationControllerState getInitialState() {
        return new ModificationControllerState(getContext());
    }
}
