package org.pyjjs.scheduler.core.api

val PROPOSED_LEAD_TIME = "task.proposed_lead_time"

interface Context {

    fun lockup(key: String): Any?

    fun put(key: String, value: Any)

}
