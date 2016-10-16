package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.ModificationControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.DataSourceChangedMessage
import org.pyjjs.scheduler.core.model.IdentifiableObject
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import kotlin.reflect.KClass

class DataSourceChangeBehaviour : Behaviour<ModificationControllerState, DataSourceChangedMessage>() {

    override fun perform(message: DataSourceChangedMessage) {
        val entityClass = message.entity.javaClass
        if (entityClass.isAssignableFrom(Task::class.java)) {
            send(actorState.taskSupervisor, message)
        } else if (entityClass.isAssignableFrom(Resource::class.java)) {
            send(actorState.resourceSupervisor, message)
        }
    }

    override fun processMessage(): KClass<DataSourceChangedMessage> {
        return DataSourceChangedMessage::class
    }
}
