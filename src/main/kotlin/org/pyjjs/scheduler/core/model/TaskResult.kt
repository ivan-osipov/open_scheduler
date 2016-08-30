package org.pyjjs.scheduler.core.model

import com.google.common.collect.Sets

class TaskResult(var task: Task) : IdentifiableObject() {

    var resourceUsages: MutableSet<ResourceUsage> = Sets.newHashSet<ResourceUsage>()
}
