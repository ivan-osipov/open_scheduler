package org.pyjjs.scheduler.core.logic.objectiveFunctions;

public abstract class ObjectiveFunction<T> {

    public abstract double calculate(T source, Context context);

    public double calculateAbsoluteDifference(T source, Context context) {
        return Math.abs(calculate(source, context) - getIdealValue());
    }

    public double getIdealValue() {
        return 0;
    }

}
