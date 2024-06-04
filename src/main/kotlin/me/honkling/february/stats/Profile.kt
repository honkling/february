package me.honkling.february.stats

import me.honkling.february.config.configToml
import me.honkling.february.stats.field.KeyField
import me.honkling.february.stats.key.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private val profiles = mutableMapOf<Player, Profile>()

class Profile(val player: Player) {
    var joinNumber by KeyField(JOIN_NUMBER)
    var bestStreak by KeyField(BEST_STREAK)
    var blockPack by KeyField(BLOCK_PACK)
    var stick by KeyField(STICK)

    var streak = 0
        set(value) {
            field = value

            if (value > bestStreak)
                bestStreak = value
        }
    
    fun getStickItem(): ItemStack {
        return configToml.sticks[stick]
            ?: configToml.sticks["starter"]
            ?: throw IllegalStateException("Missing starter stick")
    }
    
    fun getBlockItem(): ItemStack {
        return (configToml.blocks[blockPack]
            ?: configToml.blocks["starter"]
            ?: throw IllegalStateException("Missing starter block pack"))
            .primary
            .asQuantity(64)
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