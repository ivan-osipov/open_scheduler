package org.pyjjs.scheduler.core.api.impl.actors.system.messages

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message

class CheckNewChanges: Message() {
    companion object Instance {
        val INSTANCE = CheckNewChanges()
    }
}