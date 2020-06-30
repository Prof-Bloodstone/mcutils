/* Licensed under MIT */
package dev.bloodstone.mcutils.recipes

import kotlin.random.Random
import org.bukkit.entity.WanderingTrader
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.Plugin

/**
 * Adds possibility (with [chance] probability) for [WanderingTrader] to have given [recipe].
 *
 * It doesn't replace existing recipe, rather adds additional one.
 * Make sure to [register] the recipe after enabling - it's automatically unregistered on plugin disable.
 *
 * @property plugin the plugin that wants to register the recipe.
 * @property chance the chance with which the recipe will be added to [WanderingTrader] recipe list.
 * @property recipe the recipe which should be added.
 */
class WanderingTraderRecipe(private val plugin: Plugin, val chance: Int, val recipe: TradingRecipe) : Listener {
    init {
        require(chance > 0) { "WanderingTrader recipe chance can't be 0 or less (got: $chance)." }
        require(chance <= 100) { "WanderingTrader recipe chance can't be over 100 (got: $chance)." }
    }

    private fun shouldAddRecipe(): Boolean {
        val r = Random.nextInt(0, 100) // Until is exclusive
        return chance > r
    }

    @EventHandler(ignoreCancelled = true)
    fun onCreatureSpawnEvent(event: CreatureSpawnEvent) {
        val trader = event.entity as? WanderingTrader ?: return
        if (!shouldAddRecipe()) return
        val recipes = trader.recipes.toMutableList()
        recipes.add(recipe.getWithRandomIngredientAndUseCount())
        trader.recipes = recipes
    }

    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    fun unregister() {
        HandlerList.unregisterAll(this)
    }
}
