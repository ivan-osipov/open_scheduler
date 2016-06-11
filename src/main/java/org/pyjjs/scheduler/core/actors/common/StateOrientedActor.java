package org.pyjjs.scheduler.core.actors.common;

import akka.actor.UntypedActor;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.logging.Logger;

public abstract class StateOrientedActor<T extends ActorState> extends UntypedActor{
    protected static Logger LOG = Logger.getLogger(StateOrientedActor.class.getName());

    protected T state = getInitialState();

    protected abstract T getInitialState();
}
