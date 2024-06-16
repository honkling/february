@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.instance
import me.honkling.february.lib.mm
import me.honkling.february.scheduler
import me.honkling.february.stats.profile
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.block.BlockPlaceEvent

private fun onPlaceBlock(event: BlockPlaceEvent) {
    val player = event.player
    val block = event.block

    if (player.gameMode == GameMode.CREATIVE)
        return

    event.itemInHand.amount = 64

    if (block.type == Material.COBWEB)
        scheduler.scheduleSyncDelayedTask(instance, {
            event.block.type = Material.AIR
        }, 20 * 5L)

    val blockPack = player.profile.getBlockItems()

    scheduler.scheduleSyncDelayedTask(instance, {
        event.block.type = blockPack.secondary.type
    }, 20 * 3L)

    scheduler.scheduleSyncDelayedTask(instance, {
        event.block.type = blockPack.tertiary.type
    }, 20 * 5L)

    scheduler.scheduleSyncDelayedTask(instance, {
        event.block.type = Material.AIR
    }, 20 * 6L)
}