package org.pyjjs.scheduler.core.model;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class DateRange {

    private Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        checkNotNull(start);
        checkNotNull(end);
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public long getDuration(){
        return end.getTime() - start.getTime();
    }


}
