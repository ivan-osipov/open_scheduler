package org.pyjjs.scheduler.core.api.impl.actors.system.messages

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.model.IdentifiableObject

open class DataSourceChangedMessage(var entity: IdentifiableObject) : Message()

class EntityCreatedMessage(entity: IdentifiableObject) : DataSourceChangedMessage(entity)

class EntityRemovedMessage(entity: IdentifiableObject) : DataSourceChangedMessage(entity)

class EntityUpdatedMessage(entity: IdentifiableObject) : DataSourceChangedMessage(entity)