package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours;

import com.google.common.collect.Maps;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorStateInteraction;
import org.pyjjs.scheduler.core.api.impl.actors.common.StateOrientedActor;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.InitEntityAgentMessage;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;

import java.util.Map;

public abstract class BehaviourBasedActor<T extends ActorState> extends StateOrientedActor<T> {

    private Map<Class<? extends Message>, Behaviour<T, ? extends Message>> behaviours = Maps.newHashMap();

    protected <B extends Behaviour<T, ? extends Message>> void addBehaviour(Class<B> behaviourClass) {
        Behaviour<T, ? extends Message> behaviour;
        try {
            behaviour = behaviourClass.newInstance();
            addBehaviour(behaviour);
        } catch (Exception e) {
            LOG.error("Developer error:", e);
        }
    }

    private void addBehaviour(Behaviour<T, ? extends Message> behaviour) {
        behaviours.put(behaviour.processMessage(), behaviour);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        init();
    }

    protected abstract void init();

    @Override
    public void onReceive(Object o) throws Exception {
        if (checkAbilityToHandle(o)) {
            Message message = (Message) o;

            behaviours.entrySet().stream()
                    .filter(behaviourEntry -> behaviourEntry.getKey().isAssignableFrom(o.getClass()) && behaviourEntry.getValue() != null)
                    .forEach(behaviourEntry -> {
                        Behaviour<T, ? extends Message> behaviour = behaviourEntry.getValue();
                        behaviour.setActorStateInteraction(new ActorStateInteraction<T>() {
                            @Override
                            public T getActorState() {
                                return getCopyOfActorState();
                            }

                            @Override
                            public void saveActorState(T actorState) {
                                updateActorState(actorState);
                            }
                        });
                        behaviour.perform(castMessage(message));
                    });
        }
    }

    private boolean checkAbilityToHandle(Object o) {
        ActorState actorState = getCopyOfActorState();
        if(actorState.isInitialized() && (o instanceof Message)) return true;
        if(o instanceof InitEntityAgentMessage) return true;
        getSelf().tell(o, getSender());
        return false;
    }

    /**
     * unchecked reason: {@link BehaviourBasedActor#addBehaviour(Behaviour)}
     */
    @SuppressWarnings("unchecked")
    private <E extends M, M> E castMessage(M message) {
        return (E) message;
    }
}
