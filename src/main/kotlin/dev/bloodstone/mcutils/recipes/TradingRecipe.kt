/* Licensed under MIT */
package dev.bloodstone.mcutils.recipes

import kotlin.random.Random
import kotlin.random.nextInt
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe

data class TradingRecipe(
    val result: ItemStack,
    val uses: IntRange,
    val ingredients: List<RandomizedItemStack>
) {
    init {
        require(uses.first > 0) { "Trading recipe uses can't be 0 or less (got: ${uses.first})." }
    }
    fun getWithRandomIngredientAndUseCount(): MerchantRecipe {
        val recipe = MerchantRecipe(result, Random.nextInt(uses))
        recipe.ingredients = ingredients.map { it.getWithRandomAmount() }
        return recipe
    }
}
