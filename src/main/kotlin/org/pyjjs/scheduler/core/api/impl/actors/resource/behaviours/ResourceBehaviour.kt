package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState

abstract class ResourceBehaviour<M : Message> : Behaviour<ResourceActorState, M>()