package org.pyjjs.scheduler.core.api.impl.actors.resource.placing

interface PlacementFinder {
    fun findAnyPlacement(timeSheet: TimeSheet)
}