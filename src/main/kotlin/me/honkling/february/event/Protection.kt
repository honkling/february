@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.task.DROP_BOUND
import org.bukkit.GameMode
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerDropItemEvent

fun onPlaceBlock(event: BlockPlaceEvent) {
    val player = event.player
    val block = event.block

    if (player.gameMode == GameMode.CREATIVE)
        return

    if (block.y > DROP_BOUND)
        event.isCancelled = true
}

fun onDropItem(event: PlayerDropItemEvent) {
    event.isCancelled = true
}