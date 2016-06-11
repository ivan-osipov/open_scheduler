package org.pyjjs.scheduler.core.actors.system.behaviours;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.StatelessBehaviour;
import org.pyjjs.scheduler.core.actors.system.messages.DataSourceChangedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityRemovedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityUpdatedMessage;

public class DataSourceChangeBehaviour extends StatelessBehaviour<DataSourceChangedMessage> {

    private static DataSourceChangeBehaviour INSTANCE;

    protected DataSourceChangeBehaviour(ActorRef actorRef) {
        super(actorRef);
    }

    @Override
    protected void perform(DataSourceChangedMessage message) {
        if(message instanceof EntityCreatedMessage) {
            onCreate((EntityCreatedMessage)message);
        } else if(message instanceof EntityUpdatedMessage) {
            onUpdate((EntityUpdatedMessage)message);
        } else if(message instanceof EntityRemovedMessage) {
            onRemove((EntityRemovedMessage)message);
        }
    }

    @Override
    protected Class<DataSourceChangedMessage> processMessage() {
        return DataSourceChangedMessage.class;
    }

    private void onCreate(EntityCreatedMessage message) {
        //TODO
        System.out.println("Created new entity: " + message.getEntity().toString());
    }

    private void onUpdate(EntityUpdatedMessage message) {
        //TODO
        System.out.println("Updated entity: " + message.getEntity().toString());
    }

    private void onRemove(EntityRemovedMessage message) {
        //TODO
        System.out.println("Removed entity: " + message.getEntity().toString());
    }

    public static DataSourceChangeBehaviour get(ActorRef actorRef) {
        if(INSTANCE == null) {
            synchronized (DataSourceChangeBehaviour.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DataSourceChangeBehaviour(actorRef);
                }
            }
        }
        return INSTANCE;
    }
}
