package org.pyjjs.scheduler.core.actors.common;

import akka.actor.UntypedActor;
import com.google.common.collect.Maps;

import java.util.Map;

public abstract class StatelessBehaviourBasedActor extends UntypedActor {

    private Map<Class<? extends Message>, StatelessBehaviour<? extends Message>> behaviours = Maps.newHashMap();

    protected void addBehaviour(StatelessBehaviour<? extends Message> behaviour) {
        behaviours.put(behaviour.processMessage(), behaviour);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Message) {
            Message castedMessage = (Message) o;
            for (Map.Entry<Class<? extends Message>, StatelessBehaviour<? extends Message>> behaviourEntry : behaviours.entrySet()) {
                if(behaviourEntry.getKey().isAssignableFrom(o.getClass())) {
                    StatelessBehaviour<? extends Message> behaviour = behaviourEntry.getValue();
                    if (behaviour != null) {
                        behaviour.perform(castMessage(castedMessage));
                    }
                }
            }
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        initialize();
    }

    protected abstract void initialize();

    /**
     * unchecked reason: {@link StatelessBehaviourBasedActor#addBehaviour(StatelessBehaviour)}
     */
    @SuppressWarnings("unchecked")
    private <E extends M, M> E castMessage(M message) {
        return (E) message;
    }
}
