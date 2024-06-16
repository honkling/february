@file:Listener

package me.honkling.february.event

import me.honkling.commando.common.annotations.Listener
import me.honkling.february.config.configToml
import me.honkling.february.lib.mm
import me.honkling.february.lib.sendBroadcast
import me.honkling.february.stats.clearProfile
import me.honkling.february.stats.joins
import me.honkling.february.stats.profile
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

private fun onJoin(event: PlayerJoinEvent) {
    val player = event.player
    val profile = player.profile
    var joinMessage = "<dark_gray>(<green>+</green>)</dark_gray> <gray>${player.name} joined.".mm()

    if (!player.hasPlayedBefore()) {
        profile.joinNumber = joins + 1
        joins++

        joinMessage = joinMessage
            .append(" <b><dark_gray>(<dark_aqua><b:false>#$joins</dark_aqua>)".mm())
    }

    event.joinMessage(joinMessage)
    profile.respawn()
}

private fun onQuit(event: PlayerQuitEvent) {
    val player = event.player

    event.quitMessage("<dark_gray>(<red>-</red>)</dark_gray> <gray>${player.name} left.".mm())
    player.gameMode = GameMode.ADVENTURE
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
    player.gameMode = GameMode.ADVENTURE
    event.drops.clear()
    player.profile.respawn()

    val damageEvent = player.lastDamageCause ?: return
    if (damageEvent !is EntityDamageByEntityEvent || damageEvent.damager !is Player)
        return

    val killer = damageEvent.damager as Player
    val killerProfile = killer.profile
    killerProfile.refreshKit()
    killerProfile.streak++
    killerProfile.kills++
    updateBoard(killer)

    if (killerProfile.streak.mod(5) == 0)
        Bukkit.getServer().sendBroadcast("<p>${killer.name}</p> is on a <b><p>${killerProfile.streak} KILL STREAK!</b>")
}

private fun onHungerChange(event: FoodLevelChangeEvent) {
    event.isCancelled = true
}