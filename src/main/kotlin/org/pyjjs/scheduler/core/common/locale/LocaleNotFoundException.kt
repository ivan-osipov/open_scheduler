package org.pyjjs.scheduler.core.common.locale

class LocaleNotFoundException(private val reason: String) : Exception() {
    override val message: String?
        get() = "Locale not found. Reason: " + reason
}
