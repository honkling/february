package me.honkling.february.stats

import me.honkling.february.config.ConfigToml
import me.honkling.february.config.configToml
import me.honkling.february.event.updateBoard
import me.honkling.february.instance
import me.honkling.february.scheduler
import me.honkling.february.stats.field.KeyField
import me.honkling.february.stats.key.*
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private val profiles = mutableMapOf<Player, Profile>()

class Profile(val player: Player) {
    var joinNumber by KeyField(JOIN_NUMBER)
    var bestStreak by KeyField(BEST_STREAK)
    var kills by KeyField(KILLS)
    var deaths by KeyField(DEATHS)

    var blockPack by KeyField(BLOCK_PACK)
    var stick by KeyField(STICK)

    var streak = 0
        set(value) {
            field = value

            if (value > bestStreak)
                bestStreak = value
        }
    
    fun getStickItem(): ItemStack {
        val stack = (configToml.sticks[stick]
            ?: configToml.sticks["starter"]
            ?: throw IllegalStateException("Missing starter stick"))

        stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1)
        stack.itemFlags.add(ItemFlag.HIDE_ENCHANTS)

        return stack
    }

    fun getBlockItems(): ConfigToml.BlockPack {
        return configToml.blocks[blockPack]
            ?: configToml.blocks["starter"]
            ?: throw IllegalStateException("Missing starter block pack")
    }
    
    fun getBlockItem(): ItemStack {
        return getBlockItems()
            .primary
            .asQuantity(64)
    }

    fun respawn() {
        deaths++
        streak = 0
        player.spigot().respawn()
        player.inventory.clear()
        player.gameMode = GameMode.ADVENTURE
        player.teleport(configToml.spawn)
        player.foodLevel = 20
        updateBoard(player)
    }

    fun refreshKit() {
        player.inventory.clear()
        player.inventory.addItem(
            getStickItem(),
            getBlockItem(),
            ItemStack(Material.ENDER_PEARL),
            ItemStack(Material.COBWEB)
        )
    }
}

fun Player.clearProfile() = profiles.remove(this)

val Player.profile: Profile
    get() {
        if (this in profiles)
            return profiles[this]!!

        profiles[this] = Profile(this)
        return profiles[this]!!
    }