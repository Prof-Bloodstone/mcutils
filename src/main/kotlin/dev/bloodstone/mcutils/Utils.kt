package dev.bloodstone.mcutils

import dev.bloodstone.mcutils.extensions.itemstack.notNullMeta
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

data class EnableableEntry<T>(val isEnabled: Boolean, val entry: T)

data class PersistentNamespacedFlag<Z>(val key: NamespacedKey, val type: PersistentDataType<*,Z>, val value: Z) {
    fun isIn(persistentDataContainer: PersistentDataContainer) : Boolean {
        if (persistentDataContainer.get(key, type) != value) return false
        return true
    }
    fun isIn(meta: ItemMeta) : Boolean {
        return isIn(meta.persistentDataContainer)
    }
    fun isIn(item: ItemStack) : Boolean {
        return isIn(item.notNullMeta)
    }
    fun applyTo(persistentDataContainer: PersistentDataContainer) {
        persistentDataContainer.set(key, type, value)
    }
    fun applyTo(meta: ItemMeta) {
        applyTo(meta.persistentDataContainer)
    }
    fun applyTo(item: ItemStack) {
        val meta = item.notNullMeta
        applyTo(meta)
        item.notNullMeta = meta
    }
}
