package org.pyjjs.scheduler.core.data

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import org.pyjjs.scheduler.core.model.IdentifiableObject

abstract class ObservableDataSource : MutableCollection<IdentifiableObject> {

    private val dataSourceListeners: Multimap<Class<out IdentifiableObject>, DataSourceListener>

    init {
        dataSourceListeners = ArrayListMultimap.create<Class<out IdentifiableObject>, DataSourceListener>()
    }

    fun addDataSourceListener(dataSourceListener: DataSourceListener) {
        dataSourceListeners.put(IdentifiableObject::class.java, dataSourceListener)
    }

    fun removeDataSourceListener(dataSourceListener: DataSourceListener) {
        dataSourceListeners.remove(IdentifiableObject::class.java, dataSourceListener)
    }

    fun addDataSourceListener(interestType: Class<out IdentifiableObject>, dataSourceListener: DataSourceListener) {
        dataSourceListeners.put(interestType, dataSourceListener)
    }

    fun removeDataSourceListener(interestType: Class<out IdentifiableObject>, dataSourceListener: DataSourceListener) {
        dataSourceListeners.remove(interestType, dataSourceListener)
    }

    fun addDataSourceListener(interestTypes: List<Class<out IdentifiableObject>>, dataSourceListener: DataSourceListener) {
        for (interestType in interestTypes) {
            dataSourceListeners.put(interestType, dataSourceListener)
        }
    }

    fun removeDataSourceListener(interestTypes: List<Class<out IdentifiableObject>>, dataSourceListener: DataSourceListener) {
        for (interestType in interestTypes) {
            dataSourceListeners.remove(interestType, dataSourceListener)
        }
    }

    fun notifyAboutCreate(entity: IdentifiableObject) {
        notify(entity, { listener -> listener.onCreate(entity) })
    }

    fun notifyAboutUpdate(entity: IdentifiableObject) {
        notify(entity, { listener -> listener.onUpdate(entity) })
    }

    fun notifyAboutRemove(entity: IdentifiableObject) {
        notify(entity, { listener -> listener.onRemove(entity) })
    }

    private fun notify(entity: IdentifiableObject, notification: (DataSourceListener) -> Unit) {
        dataSourceListeners.entries().forEach { listenerEntry ->
            if (listenerEntry.key.isAssignableFrom(entity.javaClass)) {
                notification.invoke(listenerEntry.value)
            }
        }
    }

    interface DataSourceListener {

        fun onCreate(entity: IdentifiableObject)

        fun onUpdate(entity: IdentifiableObject)

        fun onRemove(entity: IdentifiableObject)

    }
}
