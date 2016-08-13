package org.pyjjs.scheduler.core.api.impl.actors.resource.placing.algorithms;

import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeLine;

import java.util.Set;

public interface PlacingAlgorithm {

    void configurate(Set<TimeLine.TimePart> freeTimes, Set<TimeLine.UsedTime> usedTimes);

    void findPlacement(Long duration, boolean shared);



}
