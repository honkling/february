@file:Command("spawn")

package me.honkling.february.command

import me.honkling.commando.common.annotations.Command
import me.honkling.february.config.configToml
import org.bukkit.entity.Player

fun spawn(player: Player) {
    player.health = 0.0
}