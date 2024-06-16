@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.lib.mm
import me.honkling.february.task.DROP_BOUND
import org.bukkit.GameMode
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerDropItemEvent

private fun onPlaceBlock(event: BlockPlaceEvent) {
    val player = event.player
    val block = event.block

    if (player.gameMode == GameMode.CREATIVE)
        return

    if (block.y > DROP_BOUND) {
        player.sendMessage("Cancelled by protection!".mm())
        event.isCancelled = true
    }
}

private fun onBreakBlock(event: BlockBreakEvent) {
    val player = event.player

    if (player.gameMode == GameMode.CREATIVE)
        return

    event.isCancelled = true
}

private fun onDropItem(event: PlayerDropItemEvent) {
    event.isCancelled = true
}