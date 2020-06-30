/* Licensed under MIT */
package dev.bloodstone.mcutils.recipes

import dev.bloodstone.mcutils.extensions.itemstack.amount
import kotlin.random.Random
import kotlin.random.nextInt
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Represents [ItemStack] with randomized amount, within given range.
 */
data class RandomizedItemStack(val item: ItemStack, val amountRange: IntRange) : ItemStack(item) {
    constructor(item: ItemStack, amount: Int) : this(item, IntRange(amount, amount))
    constructor(item: ItemStack) : this(item, item.amount)
    constructor(type: Material, amountRange: IntRange) : this(ItemStack(type), amountRange)
    constructor(type: Material, amount: Int) : this(type, IntRange(amount, amount))
    init {
        require(amountRange.first > 0) { "RandomizedItemStack min amount can't be 0 or less (got: ${amountRange.first})." }
        require(amountRange.last <= 64) { "RandomizedItemStack max amount can't exceed 64 (got: ${amountRange.last})." }
    }

    /**
     * Returns copy of itself with amount randomly chosen from [amountRange].
     */
    fun getWithRandomAmount(): RandomizedItemStack {
        val amount = Random.nextInt(amountRange)
        val new = RandomizedItemStack(this, amountRange)
        new.amount = amount
        return new
    }
}
