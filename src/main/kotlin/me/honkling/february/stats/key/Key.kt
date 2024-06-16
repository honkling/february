package me.honkling.february.stats.key

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import kotlin.reflect.KProperty

class Key<T, Z : Any>(
    field: KProperty<Key<T, Z>>,
    val type: PersistentDataType<T, Z>,
    private val defaultValue: Z
) {
    val key = NamespacedKey("february", field.name.lowercase())

    operator fun set(holder: PersistentDataHolder, value: Z) {
        holder.persistentDataContainer.set(key, type, value)
    }

    operator fun get(holder: PersistentDataHolder): Z {
        return holder.persistentDataContainer.get(key, type) ?: defaultValue
    }

    fun delete(holder: PersistentDataHolder) {
        holder.persistentDataContainer.remove(key)
    }

    operator fun contains(holder: PersistentDataHolder): Boolean {
        return holder.persistentDataContainer.has(key, type)
    }
}