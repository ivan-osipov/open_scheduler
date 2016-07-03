package org.pyjjs.scheduler.core.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.pyjjs.scheduler.core.model.IdentifiableObject;

import java.util.*;
import java.util.function.Consumer;

public abstract class ObservableDataSource implements Collection<IdentifiableObject> {

    private Multimap<Class<? extends IdentifiableObject>, DataSourceListener> dataSourceListeners;

    public ObservableDataSource() {
        dataSourceListeners = ArrayListMultimap.create();
    }

    public void addDataSourceListener(DataSourceListener dataSourceListener) {
        dataSourceListeners.put(IdentifiableObject.class, dataSourceListener);
    }

    public void removeDataSourceListener(DataSourceListener dataSourceListener) {
        dataSourceListeners.remove(IdentifiableObject.class, dataSourceListener);
    }

    public void addDataSourceListener(Class<? extends IdentifiableObject> interestType, DataSourceListener dataSourceListener) {
        dataSourceListeners.put(interestType, dataSourceListener);
    }

    public void removeDataSourceListener(Class<? extends IdentifiableObject> interestType, DataSourceListener dataSourceListener) {
        dataSourceListeners.remove(interestType, dataSourceListener);
    }

    public void addDataSourceListener(List<Class<? extends IdentifiableObject>> interestTypes, DataSourceListener dataSourceListener) {
        for (Class<? extends IdentifiableObject> interestType : interestTypes) {
            dataSourceListeners.put(interestType, dataSourceListener);
        }
    }

    public void removeDataSourceListener(List<Class<? extends IdentifiableObject>> interestTypes, DataSourceListener dataSourceListener) {
        for (Class<? extends IdentifiableObject> interestType : interestTypes) {
            dataSourceListeners.remove(interestType, dataSourceListener);
        }
    }

    public void notifyAboutCreate(IdentifiableObject entity) {
        notify(entity, (listener) -> listener.onCreate(entity));
    }

    public void notifyAboutUpdate(IdentifiableObject entity) {
        notify(entity, (listener) -> listener.onUpdate(entity));
    }

    public void notifyAboutRemove(IdentifiableObject entity) {
        notify(entity, (listener) -> listener.onRemove(entity));
    }

    private void notify(IdentifiableObject entity, Consumer<DataSourceListener> notification) {
        dataSourceListeners.entries()
                .stream()
                .forEach(listenerEntry -> {
                    if (listenerEntry.getKey().isAssignableFrom(entity.getClass())) {
                        notification.accept(listenerEntry.getValue());
                    }
                });
    }

    public interface DataSourceListener {

        void onCreate(IdentifiableObject entity);

        void onUpdate(IdentifiableObject entity);

        void onRemove(IdentifiableObject entity);

    }
}
