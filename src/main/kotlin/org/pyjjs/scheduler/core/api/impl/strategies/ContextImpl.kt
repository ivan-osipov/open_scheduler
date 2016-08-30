package org.pyjjs.scheduler.core.api.impl.strategies

import org.pyjjs.scheduler.core.api.Context
import java.util.concurrent.ConcurrentHashMap

class ContextImpl: Context {

    private val context = ConcurrentHashMap<String, Any>()

    override fun lockup(key: String): Any? {
        checkNotNull(key, {"Key cannot be null"})
        return context[key]
    }

    override fun put(key: String, value: Any) {
        checkNotNull(key, {"Key cannot be null"})
        checkNotNull(value, {"Value cannot be null"})
        context[key] = value
    }

}
