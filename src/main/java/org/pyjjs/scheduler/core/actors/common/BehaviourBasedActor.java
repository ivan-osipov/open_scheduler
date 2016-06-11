package org.pyjjs.scheduler.core.actors.common;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class BehaviourBasedActor<T extends ActorState> extends StateOrientedActor<T> {

    private Map<Class<? extends Message>, Behaviour<T, ? extends Message>> behaviours = Maps.newHashMap();

    protected void addBehaviour(Behaviour<T, ? extends Message> behaviour) {
        behaviours.put(behaviour.processMessage(), behaviour);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Message) {
            Message castedMessage = (Message) o;

            for (Map.Entry<Class<? extends Message>, Behaviour<T, ? extends Message>> behaviourEntry : behaviours.entrySet()) {
                if(behaviourEntry.getKey().isAssignableFrom(o.getClass())) {
                    Behaviour<T, ? extends Message> behaviour = behaviourEntry.getValue();
                    if (behaviour != null) {
                        behaviour.setActorState(state);
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
