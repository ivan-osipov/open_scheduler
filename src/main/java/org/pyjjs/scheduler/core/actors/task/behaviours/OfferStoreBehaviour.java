package org.pyjjs.scheduler.core.actors.task.behaviours;

import com.typesafe.config.Config;
import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.actors.task.messages.CheckOffersMessage;

import java.util.concurrent.TimeUnit;

public class OfferStoreBehaviour extends Behaviour<TaskActorState, OfferMessage> {
    private static final String DEFAULT_NOTIFICATION_DELAY_IN_MILLIS = "scheduler.times.notification.delay.in_millis";

    @Override
    protected void perform(OfferMessage message) {
        System.out.println(message.getSender() + " предлагает разместиться за " + message.getPlacingPrice() + "$");
        TaskActorState actorState = getActorState();
        actorState.addOffer(message);
        boolean checkOffersIsScheduled = actorState.checkOffersIsScheduled();

        if(!checkOffersIsScheduled) {
            scheduleOffersCheck(actorState);
        }
        saveActorState(actorState);
    }

    private void scheduleOffersCheck(TaskActorState actorState) {
        Config schedulerConfig = getActorState().getActorSystem().settings().config();
        long notificationDelayInMillis = schedulerConfig.getLong(DEFAULT_NOTIFICATION_DELAY_IN_MILLIS);
        sendByDelay(new CheckOffersMessage(getActorRef()), notificationDelayInMillis, TimeUnit.MILLISECONDS);
        actorState.setCheckOffersScheduled(true);
    }

    @Override
    protected Class<OfferMessage> processMessage() {
        return OfferMessage.class;
    }

}
