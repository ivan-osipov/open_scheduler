package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.system.ModificationControllerState;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.DataSourceChangedMessage;
import org.pyjjs.scheduler.core.model.IdentifiableObject;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.model.Task;

public class DataSourceChangeBehaviour extends Behaviour<ModificationControllerState, DataSourceChangedMessage> {

    private static DataSourceChangeBehaviour INSTANCE = new DataSourceChangeBehaviour();

    @Override
    protected void perform(DataSourceChangedMessage message) {
        Class<? extends IdentifiableObject> entityClass = message.getEntity().getClass();
        if(entityClass.isAssignableFrom(Task.class)) {
            send(getActorState().getTaskSupervisor(), message);
        } else if(entityClass.isAssignableFrom(Resource.class)) {
            send(getActorState().getResourceSupervisor(), message);
        }
    }

    @Override
    protected Class<DataSourceChangedMessage> processMessage() {
        return DataSourceChangedMessage.class;
    }

    public static DataSourceChangeBehaviour get() {
        return INSTANCE;
    }
}
