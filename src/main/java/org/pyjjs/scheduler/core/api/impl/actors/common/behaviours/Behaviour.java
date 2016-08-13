package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorStateInteraction;
import org.pyjjs.scheduler.core.common.SystemConfigKeys;
import org.pyjjs.scheduler.core.common.locale.LangResolver;
import org.pyjjs.scheduler.core.common.locale.LocaleNotFoundException;
import org.pyjjs.scheduler.core.common.locale.LocaleResolver;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.FiniteDuration;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public abstract class Behaviour<T extends ActorState, M extends Message> {
    public final Logger BEHAVIOUR_LOG = LoggerFactory.getLogger(getClass());

    private ActorStateInteraction<T> actorStateInteraction;

    private T actorState;
    private LocaleResolver localeResolver;

    protected Behaviour(){}

    protected abstract void perform(M message);

    protected void answer(M inMessage, Message answerMessage) {
        inMessage.getSender().tell(answerMessage, getActorRef());
    }

    protected void send(ActorRef receiver, Message message) {
        receiver.tell(message, getActorRef());
    }

    protected void sendToAll(Collection<ActorRef> receivers, Message message) {
        receivers.stream().forEach((receiver) -> send(receiver, message));
    }

    protected ActorRef getActorRef() {
        return getActorState().getActorRef();
    }

    protected String getActorLocalName() {
        return getActorLocalName(getActorRef());
    }

    protected String getActorLocalName(ActorRef actorRef) {
        return actorRef.path().name();
    }

    protected void saveActorState(T actorState) {
        actorStateInteraction.saveActorState(actorState);
    }

    public T getActorState() {
        return actorState;
    }

    public void setActorStateInteraction(ActorStateInteraction<T> actorStateInteraction) {
        this.actorStateInteraction = actorStateInteraction;
        this.actorState = actorStateInteraction.getActorState();
        try {
            LangResolver langResolver = getLangResolver();
            this.localeResolver = LocaleResolver.get(langResolver);
        } catch (LocaleNotFoundException e) {
            BEHAVIOUR_LOG.warn("Locale init problem", e);
        }
    }

    private LangResolver getLangResolver() {
        ActorSystem actorSystem = getActorState().getActorSystem();
        ActorSystem.Settings actorSystemSettings = actorSystem.settings();
        Config systemConfig = actorSystemSettings.config();
        return new LangResolver(systemConfig.getString(SystemConfigKeys.DEFAULT_LOCALE_KEY));
    }

    protected void printMessage(String messageKey, Object... properties) {
        String message = messageKey;
        if(localeResolver != null) {
            message = localeResolver.getString(messageKey, properties);
        }
        BEHAVIOUR_LOG.info(message, properties);
    }

    public void sendToParent(Message message) {
        getActorState().getActorContext().parent().tell(message, getActorRef());
    }

    public void sendToResources(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/resources/*")
                .tell(message, getActorRef());
    }

    public void sendToResourceSupervisor(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/resources")
                .tell(message, getActorRef());
    }

    public void sendToTaskSupervisor(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/tasks")
                .tell(message, getActorRef());
    }

    public void sendToSelf(Message message) {
        send(getActorRef(), message);
    }

    public void sendByDelay(Message message, long delay, TimeUnit timeUnit) {
        getActorState().getActorSystem()
                .scheduler()
                .scheduleOnce(
                        FiniteDuration.apply(delay, timeUnit),
                        ()-> sendToSelf(message),
                        getActorState().getActorContext().dispatcher());
    }

    public void sendToTasks(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/tasks/*")
                .tell(message, getActorRef());
    }

    protected abstract Class<M> processMessage();
}
