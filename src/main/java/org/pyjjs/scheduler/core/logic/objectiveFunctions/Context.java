package org.pyjjs.scheduler.core.logic.objectiveFunctions;

public interface Context {

    Object lockup(String key);

    void put(String key, Object value);

}
