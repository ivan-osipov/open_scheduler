package org.pyjjs.scheduler.core.model;

public class ResourceUsage extends IdentifiableObject{
    private Resource resource;
    private DateRange dateRange;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
