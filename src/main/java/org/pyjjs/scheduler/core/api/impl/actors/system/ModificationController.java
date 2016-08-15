package org.pyjjs.scheduler.core.api.impl.actors.system;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.DataSourceChangeBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Promise;

public class ModificationController extends BehaviourBasedActor<ModificationControllerState> {

    private static final Logger LOG = LoggerFactory.getLogger(ModificationController.class);


    public ModificationController(ActorRef taskSupervisor, ActorRef resourceSupervisor) {
        ModificationControllerState state = getCopyOfActorState();
        state.setTaskSupervisor(taskSupervisor);
        state.setResourceSupervisor(resourceSupervisor);
        state.setInitialized(true);
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
