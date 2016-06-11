package org.pyjjs.scheduler.core.data;

import com.google.common.collect.Lists;
import org.pyjjs.scheduler.core.model.primary.IdentifiableObject;

import javax.annotation.Nonnull;
import java.util.*;

public class HashSetDataSourceImpl extends ObservableDataSource {

    private Set<IdentifiableObject> data;

    public HashSetDataSourceImpl() {
        data = new HashSet<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    @Nonnull
    public Iterator<IdentifiableObject> iterator() {
        return data.iterator();
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    @Nonnull
    public <T> T[] toArray(@Nonnull T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(IdentifiableObject entity) {
        boolean added = data.add(entity);
        if(added) {
            notifyAboutCreate(entity);
        } else {
            data.remove(entity);
            data.add(entity);
            notifyAboutUpdate(entity);
        }
        return added;

    }

    @Override
    public boolean remove(Object o) {
        boolean removed = data.remove(o);
        if(removed) {
            notifyAboutRemove((IdentifiableObject) o);
        }
        return removed;
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends IdentifiableObject> c) {
        boolean changed = false;
        for (IdentifiableObject identifiableObject : c) {
            changed |= add(identifiableObject);
        }
        return changed;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            changed |= remove(o);
        }
        return changed;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        List<IdentifiableObject> removedData = Lists.newArrayList(data);
        removedData.removeAll(c);
        data.retainAll(c);
        for (IdentifiableObject entity : removedData) {
            notifyAboutRemove(entity);
        }
        return removedData.size() > 0;
    }

    @Override
    public void clear() {
        removeAll(data);
    }


}
