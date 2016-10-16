package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorStateInteraction
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.common.SystemConfigKeys
import org.pyjjs.scheduler.core.common.locale.LangResolver
import org.pyjjs.scheduler.core.common.locale.LocaleResolver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

abstract class Behaviour<T : ActorState, M : Message> protected constructor() {
    val BEHAVIOUR_LOG: Logger = LoggerFactory.getLogger(javaClass)

    private lateinit var actorStateInteraction: ActorStateInteraction<T>

    lateinit var actorState: T
        private set
    private var localeResolver: LocaleResolver? = null

    fun invoke(message: Message) {
        perform(processMessage().java.cast(message))
    }
    abstract fun perform(message: M)

    protected fun <M: Message> answer(inMessage: M, answerMessage: Message) {
        inMessage.sender!!.tell(answerMessage, actorRef)
    }

    protected fun send(receiver: ActorRef, message: Message) {
        receiver.tell(message, actorRef)
    }

    protected fun sendToAll(receivers: Collection<ActorRef>, message: Message) {
        receivers.forEach { receiver -> send(receiver, message) }
    }

    protected val actorRef: ActorRef
        get() = actorState.actorRef

    protected val actorLocalName: String
        get() = getActorLocalName(actorRef)

    protected fun getActorLocalName(actorRef: ActorRef): String {
        return actorRef.path().name()
    }

    protected fun saveActorState(actorState: T) {
        actorStateInteraction.saveActorState(actorState)
    }

    fun setActorStateInteraction(actorStateInteraction: ActorStateInteraction<T>) {
        this.actorStateInteraction = actorStateInteraction
        this.actorState = actorStateInteraction.getActorState()
        this.localeResolver = LocaleResolver.get(langResolver)
    }

    private val langResolver: LangResolver
        get() {
            val actorSystem = actorState.actorSystem
            val actorSystemSettings = actorSystem.settings()
            val systemConfig = actorSystemSettings.config()
            return LangResolver(systemConfig.getString(SystemConfigKeys.DEFAULT_LOCALE_KEY))
        }

    protected fun logMessage(messageKey: String, vararg properties: Any) {
        val message = localeResolver?.getString(messageKey, *properties) ?: messageKey
        BEHAVIOUR_LOG.info(message, *properties)
    }

    fun sendToParent(message: Message) {
        actorState.actorContext.parent().tell(message, actorRef)
    }

    fun publishToEventStream(message: Message) {
        actorState.actorContext.system().eventStream().publish(message)
    }

    fun sendToResources(message: Message) {
        actorState.actorContext.system().actorSelection("user/resources/*").tell(message, actorRef)
    }

    fun sendToResourceSupervisor(message: Message) {
        actorState.actorContext.system().actorSelection("user/resources").tell(message, actorRef)
    }

    fun sendToTaskSupervisor(message: Message) {
        actorState.actorContext.system().actorSelection("user/tasks").tell(message, actorRef)
    }

    fun sendToSelf(message: Message) {
        send(actorRef, message)
    }

    fun sendByDelay(message: Message, delay: Long, timeUnit: TimeUnit) {
        actorState.actorSystem.scheduler().scheduleOnce(
                FiniteDuration.apply(delay, timeUnit),
                { sendToSelf(message) },
                actorState.actorContext.dispatcher())
    }

    fun sendToTasks(message: Message) {
        actorState.actorContext.system().actorSelection("user/tasks/*").tell(message, actorRef)
    }

    fun scheduleNotification(notification: Message, timeKey: String) {
        val schedulerConfig = actorState.actorSystem.settings().config()
        val notificationDelayInMillis = schedulerConfig.getLong(timeKey)
        sendByDelay(notification, notificationDelayInMillis, TimeUnit.MILLISECONDS)
    }

    abstract fun processMessage(): KClass<out M>
}
