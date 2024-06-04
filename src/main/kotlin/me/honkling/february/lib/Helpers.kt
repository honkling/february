package me.honkling.february.lib

import org.bukkit.Bukkit
import org.bukkit.entity.Player

val players: List<Player>
    get() = Bukkit.getOnlinePlayers().toList()