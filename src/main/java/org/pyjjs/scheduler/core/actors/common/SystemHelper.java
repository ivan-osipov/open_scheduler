package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class SystemHelper {

    public static ActorRef createActorByType(ActorSystem actorSystem, Class<? extends UntypedActor> actorType) {
        return actorSystem.actorOf(Props.create(actorType));
    }

    public static ActorRef createActorByType(ActorSystem actorSystem, Class<? extends UntypedActor> actorType, String path) {
        return actorSystem.actorOf(Props.create(actorType),path);
    }

}
