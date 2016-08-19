package org.pyjjs.scheduler.core.data

import com.google.common.collect.Lists
import org.pyjjs.scheduler.core.model.IdentifiableObject
import java.util.*

class HashSetDataSourceImpl : ObservableDataSource() {

    private val data: MutableSet<IdentifiableObject>

    init {
        data = HashSet<IdentifiableObject>()
    }

    override val size: Int
        get() = data.size

    override fun contains(element: IdentifiableObject): Boolean {
        return data.contains(element)
    }

    override fun containsAll(elements: Collection<IdentifiableObject>): Boolean {
        return data.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    override fun iterator(): MutableIterator<IdentifiableObject> {
        return data.iterator()
    }

    override fun add(element: IdentifiableObject): Boolean {
        val added = data.add(element)
        if (added) {
            notifyAboutCreate(element)
        } else {
            data.remove(element)
            data.add(element)
            notifyAboutUpdate(element)
        }
        return added

    }

    override fun remove(element: IdentifiableObject): Boolean {
        val removed = data.remove(element)
        if (removed) {
            notifyAboutRemove(element)
        }
        return removed
    }

    override fun removeAll(elements: Collection<IdentifiableObject>): Boolean {
        var changed = false
        for (o in elements) {
            changed = changed or remove(o)
        }
        return changed
    }

    override fun retainAll(elements: Collection<IdentifiableObject>): Boolean {
        val removedData = Lists.newArrayList(data)
        removedData.removeAll(elements)
        data.retainAll(elements)
        for (entity in removedData) {
            notifyAboutRemove(entity)
        }
        return removedData.size > 0
    }

    override fun addAll(elements: Collection<IdentifiableObject>): Boolean {
        var changed = false
        for (identifiableObject in elements) {
            changed = changed or add(identifiableObject)
        }
        return changed
    }

    override fun clear() {
        removeAll(data)
    }

}
