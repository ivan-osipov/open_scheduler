package org.pyjjs.scheduler.core.api.impl.actors.resource.placing;

import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.algorithms.PlacingAlgorithm;

import javax.annotation.Nullable;
import java.util.*;

public class TimeLine {

    private Set<TimePart> freeTimes = new TreeSet<>((o1, o2) -> o1.start.compareTo(o2.start));
    private Set<UsedTime> usedTimes = new TreeSet<>((o1, o2) -> o1.timePart.start.compareTo(o2.timePart.start));

    private PlacingAlgorithm placingAlgorithm;
    private Long planningHorizon = Long.MAX_VALUE;

    public TimeLine() {
        freeTimes.add(new TimePart(0L));
    }

    public void setPlacingAlgorithm(PlacingAlgorithm placingAlgorithm) {
        this.placingAlgorithm = placingAlgorithm;
    }

    @Nullable
    public TimePart findNearestFreeTime() {
        Iterator<TimePart> freeTimeIterator = freeTimes.iterator();
        if(freeTimeIterator.hasNext()) {
            return freeTimeIterator.next();
        }
        return null;
    }

    @Nullable
    public UsedTime findNearestUsedTime() {
        Iterator<UsedTime> freeTimeIterator = usedTimes.iterator();
        if(freeTimeIterator.hasNext()) {
            return freeTimeIterator.next();
        }
        return null;
    }

    public class UsedTime {
        private TimePart timePart;
        private Object tenant;

        public UsedTime(Object tenant) {
            this.tenant = tenant;
            this.timePart = new TimePart();
        }

        public void setTimePart(TimePart timePart) {
            this.timePart = timePart;
        }

        public TimePart getTimePart() {
            return timePart;
        }

        public Object getTenant() {
            return tenant;
        }

        public void setTenant(Object tenant) {
            this.tenant = tenant;
        }
    }

    public class TimePart {
        private Long start = 0L;
        private Long duration = 0L;

        public TimePart() {
        }

        public TimePart(Long start) {
            this.start = start;
        }

        public Long getStart() {
            return start;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }
    }
}
