package org.pyjjs.scheduler.core.actors.system;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.system.behaviours.DataSourceChangeBehaviour;

public class ModificationController extends BehaviourBasedActor<ModificationControllerState> {

    public ModificationController(ActorRef taskSupervisor, ActorRef resourceSupervisor) {
        ModificationControllerState state = getCopyOfActorState();
        state.setTaskSupervisor(taskSupervisor);
        state.setResourceSupervisor(resourceSupervisor);
        updateActorState(state);
    }

    @Override
    protected void init() {
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(DataSourceChangeBehaviour.get());
    }

    @Override
    protected ModificationControllerState getInitialState() {
        return new ModificationControllerState(getContext());
    }
}
