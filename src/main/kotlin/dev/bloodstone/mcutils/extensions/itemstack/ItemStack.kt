/* Licensed under MIT */
package dev.bloodstone.mcutils.extensions.itemstack
/*
* This was originally written in Java by @MiniDigger
* Post: https://bukkit.org/threads/util-itembuilder-chainable-itemstack-builder-v1-2.318326/
* Code: https://gist.github.com/MiniDigger/d91a13514b11603bceda
* Was later partially ported to Kotlin by @yamalidon https://www.spigotmc.org/threads/kotlin-itembuilder.361195/
* And then modified and improved by @Prof_Bloodstone
*/
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta

fun ItemStack.amount(amount: Int): ItemStack {
    setAmount(amount)
    return this
}

/**
 * Only rare, specific items have null meta.
 * This convenience property let's avoid checking for null every time,
 * if developer is sure that [ItemMeta] exists.
 */
var ItemStack.notNullMeta: ItemMeta
    get() {
        return itemMeta ?: throw NullPointerException("$type doesn't have metadata")
    }
    set(im: ItemMeta) {
        itemMeta = im
    }

fun ItemStack.name(name: String): ItemStack {
    val meta = notNullMeta
    meta.setDisplayName(name.colorized)
    notNullMeta = meta
    return this
}

fun ItemStack.lore(text: String): ItemStack {
    val meta = notNullMeta
    val lore: MutableList<String> = meta.lore ?: ArrayList()
    lore.add(text)
    meta.lore = lore.colorized
    notNullMeta = meta
    return this
}

fun ItemStack.lore(vararg text: String): ItemStack {
    text.forEach { this.lore(it) }
    return this
}

fun ItemStack.lore(text: List<String>): ItemStack {
    text.forEach { this.lore(it) }
    return this
}

/**
 * Add enchantment to item, without checking whether it's conflicting with other enchantments,
 * or even applicable to ItemStack.
 *
 * See [ItemStack.addUnsafeEnchantment] for more details.
 */
fun ItemStack.enchantment(enchantment: Enchantment, level: Int = 1): ItemStack {
    addUnsafeEnchantment(enchantment, level)
    return this
}

fun ItemStack.type(material: Material): ItemStack {
    type = material
    return this
}

fun ItemStack.clearLore(): ItemStack {
    val meta = notNullMeta
    meta.lore = ArrayList()
    notNullMeta = meta
    return this
}

/**
 * Makes the item glow.
 * It does that by adding an enchantment, that doesn't have an effect of given ItemStack type.
 * Additionally, sets [ItemFlag.HIDE_ENCHANTS] flag.
 */
fun ItemStack.addGlow(): ItemStack {
    val enchantment = if (type != Material.FISHING_ROD) Enchantment.LURE else Enchantment.ARROW_FIRE
    addUnsafeEnchantment(enchantment, 1)
    flag(ItemFlag.HIDE_ENCHANTS)
    return this
}

fun ItemStack.clearEnchantments(): ItemStack {
    enchantments.keys.forEach { this.removeEnchantment(it) }
    return this
}

/**
 * Change color of leather armor.
 */
fun ItemStack.color(color: Color): ItemStack {
    if (type == Material.LEATHER_BOOTS ||
        type == Material.LEATHER_CHESTPLATE ||
        type == Material.LEATHER_HELMET ||
        type == Material.LEATHER_LEGGINGS) {

        val meta = notNullMeta as LeatherArmorMeta
        meta.setColor(color)
        notNullMeta = meta
        return this
    } else {
        throw IllegalArgumentException("Colors only applicable for leather armor!")
    }
}

fun ItemStack.flag(vararg flag: ItemFlag): ItemStack {
    val meta = notNullMeta
    meta.addItemFlags(*flag)
    notNullMeta = meta
    return this
}

private val String.colorized: String
    get() {
        return ChatColor.translateAlternateColorCodes('&', this)
    }

private val List<String>.colorized: List<String>
    get() = this.map { it.colorized }
