package org.pyjjs.scheduler.core.actors.system.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.system.ModificationControllerState;
import org.pyjjs.scheduler.core.actors.system.messages.DataSourceChangedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityRemovedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityUpdatedMessage;
import org.pyjjs.scheduler.core.model.primary.IdentifiableObject;
import org.pyjjs.scheduler.core.model.primary.Resource;
import org.pyjjs.scheduler.core.model.primary.Task;

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
