package org.pyjjs.scheduler.core.model

import com.google.common.collect.Sets

class TaskResult(var task: Task?) : IdentifiableObject() {

    var resourceUsages: Set<ResourceUsage> = Sets.newHashSet<ResourceUsage>()
}
