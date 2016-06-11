package org.pyjjs.scheduler.core.actors.system;

import org.pyjjs.scheduler.core.actors.common.StatelessBehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.system.behaviours.DataSourceChangeBehaviour;

public class ModificationController extends StatelessBehaviourBasedActor {

    @Override
    public void preStart() throws Exception {
        super.preStart();
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(DataSourceChangeBehaviour.get(getSelf()));
    }
}
