package org.pyjjs.scheduler.core.actors.common.behaviours;

import com.google.common.collect.Maps;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.ActorStateInteraction;
import org.pyjjs.scheduler.core.actors.common.StateOrientedActor;
import org.pyjjs.scheduler.core.actors.common.messages.Message;

import java.util.Map;

public abstract class BehaviourBasedActor<T extends ActorState> extends StateOrientedActor<T> {

    private Map<Class<? extends Message>, Behaviour<T, ? extends Message>> behaviours = Maps.newHashMap();

    private void addBehaviour(Behaviour<T, ? extends Message> behaviour) {
        behaviours.put(behaviour.processMessage(), behaviour);
    }

    protected <B extends Behaviour<T, ? extends Message>> void addBehaviour(Class<B> behaviourClass) {
        Behaviour<T, ? extends Message> behaviour;
        try {
            behaviour = behaviourClass.newInstance();
            addBehaviour(behaviour);
        } catch (Exception e) {
            LOG.error("Developer error:", e);
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        init();
    }

    protected abstract void init();

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Message) {
            Message castedMessage = (Message) o;

            for (Map.Entry<Class<? extends Message>, Behaviour<T, ? extends Message>> behaviourEntry : behaviours.entrySet()) {
                if(behaviourEntry.getKey().isAssignableFrom(o.getClass())) {
                    Behaviour<T, ? extends Message> behaviour = behaviourEntry.getValue();
                    if (behaviour != null) {
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
                        behaviour.perform(castMessage(castedMessage));
                    }
                }
            }
        }
    }

    /**
     * unchecked reason: {@link BehaviourBasedActor#addBehaviour(Behaviour)}
     */
    @SuppressWarnings("unchecked")
    private <E extends M, M> E castMessage(M message) {
        return (E) message;
    }
}
