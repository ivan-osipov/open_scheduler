package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import com.typesafe.config.Config;
import org.pyjjs.scheduler.core.common.SystemConfigKeys;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys;
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.CheckOffersMessage;

import java.util.concurrent.TimeUnit;

public class OfferStoreBehaviour extends Behaviour<TaskActorState, OfferMessage> {

    @Override
    protected void perform(OfferMessage message) {
        printMessage(LocaleMessageKeys.TASK_RECEIVED_OFFER, message.getSender().path().name(), getActorRef().path().name(), message.getPlacingPrice());
        TaskActorState actorState = getActorState();
        actorState.addOffer(message);
        boolean checkOffersIsScheduled = actorState.checkOffersIsScheduled();

        if(!checkOffersIsScheduled) {
            scheduleOffersCheck(actorState);
        }
        saveActorState(actorState);
    }

    private void scheduleOffersCheck(TaskActorState actorState) {
        scheduleNotification(new CheckOffersMessage(getActorRef()),  SystemConfigKeys.DEFAULT_NOTIFICATION_DELAY_IN_MILLIS_KEY);
        actorState.setCheckOffersScheduled(true);
    }

    @Override
    protected Class<OfferMessage> processMessage() {
        return OfferMessage.class;
    }

}
