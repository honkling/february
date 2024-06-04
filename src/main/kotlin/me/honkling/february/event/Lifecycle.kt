@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.BRAND
import me.honkling.february.config.configToml
import me.honkling.february.lib.mm
import me.honkling.february.lib.sendBroadcast
import me.honkling.february.stats.clearProfile
import me.honkling.february.stats.joins
import me.honkling.february.stats.profile
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

private fun onJoin(event: PlayerJoinEvent) {
    val player = event.player
    var joinMessage = "<b><dark_gray>(<green><b:false>+</green>)</b> <gray>${player.name}".mm()

    if (!player.hasPlayedBefore()) {
        player.profile.joinNumber = joins + 1
        joins++

        joinMessage = joinMessage
            .append(" <b><dark_gray>(<dark_aqua><b:false>#$joins</dark_aqua>)".mm())
    }

    event.joinMessage(joinMessage)
    player.teleport(configToml.spawn)
}

private fun onQuit(event: PlayerQuitEvent) {
    val player = event.player

    event.quitMessage("<b><dark_gray>(<red><b:false>-</red>)</b> <gray>${player.name}".mm())
    player.inventory.clear()
    player.clearProfile()
}

private fun onDamage(event: EntityDamageEvent) {
    event.damage = 0.0

    if (event.cause == DamageCause.FALL)
        event.isCancelled = true
}

private fun onDeath(event: PlayerDeathEvent) {
    val player = event.player
    event.drops.clear()
    player.spigot().respawn()
    player.teleport(configToml.spawn)
    val profile = player.profile
    profile.streak = 0
    updateBoard(player)

    val killer = player.killer ?: return
    val killerProfile = killer.profile
    killerProfile.refreshKit()
    killerProfile.streak++
    updateBoard(killer)

    if (killerProfile.streak.mod(5) == 0)
        Bukkit.getServer().sendBroadcast("<p>$killer</p> is on a <b><p>${killerProfile.streak} KILL STREAK!</b>")
}