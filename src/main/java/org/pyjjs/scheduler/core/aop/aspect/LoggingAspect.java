package org.pyjjs.scheduler.core.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    @Before("execution(void org.pyjjs.scheduler.core.actors.task.behaviours.FoundResourceBehaviour.perform(..))")
    public void logBehaviourPerformBefore(JoinPoint joinPoint) {
        Object message = joinPoint.getArgs()[0];
        Behaviour behaviour = (Behaviour) joinPoint.getThis();
        Logger logger = LoggerFactory.getLogger(behaviour.getClass());
        ActorState actorState = behaviour.getActorState();
        logger.info("Actor: {} processing {} message", actorState.getActorRef(), message.getClass());
    }

}
