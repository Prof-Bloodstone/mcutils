/* Licensed under MIT */
package dev.bloodstone.mcutils.datatypes

import com.google.gson.Gson
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

/**
 * Lets you store arbitrary data in PDC.
 * @param <T> The type you want to serialize to Json
 */
class JsonDataType<T>(private val typeClass: Class<T>) : PersistentDataType<String, T> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<T> {
        return typeClass
    }

    override fun toPrimitive(complex: T, persistentDataAdapterContext: PersistentDataAdapterContext): String {
        return gson.toJson(complex)
    }

    override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext): T {
        return gson.fromJson(primitive, complexType)
    }

    companion object {
        private val gson = Gson()
    }
}
