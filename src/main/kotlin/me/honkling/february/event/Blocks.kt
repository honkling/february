@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.instance
import me.honkling.february.scheduler
import me.honkling.february.stats.profile
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.block.BlockPlaceEvent

val blocks = mutableMapOf<Int, Block>()

fun scheduleBlockBreakAnimTask(block: Block) {
    var time = 0
    var id = 0
    val blockId: Int = if (blocks.keys.size == 0) 0 else blocks.keys.sortedDescending()[0] + 1
    blocks[blockId] = block

    val task = {
        block.world.players.forEach {
            // the entity id needs to be unique per block,
            // as the mc client can only see one block break animation stage per entity id and
            // would cause flickering or the animation to break (and stay at the last stage) otherwise
            it.sendBlockDamage(block.location, time++.toFloat()/10, blockId)
        }
        if (time > 10) {
            scheduler.cancelTask(id)
            blocks.remove(blockId)
        }
    }

    id = scheduler.scheduleSyncRepeatingTask(instance, task, 0, 12)
}

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

    scheduleBlockBreakAnimTask(block)

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