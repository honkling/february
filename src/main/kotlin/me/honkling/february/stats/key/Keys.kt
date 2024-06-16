package me.honkling.february.stats.key

import org.bukkit.persistence.PersistentDataType

val JOIN_NUMBER: Key<Int, Int> = Key(::JOIN_NUMBER, PersistentDataType.INTEGER, 0)
val BEST_STREAK: Key<Int, Int> = Key(::BEST_STREAK, PersistentDataType.INTEGER, 0)
val KILLS: Key<Int, Int> = Key(::KILLS, PersistentDataType.INTEGER, 0)
val DEATHS: Key<Int, Int> = Key(::DEATHS, PersistentDataType.INTEGER, 0)
val STICK: Key<String, String> = Key(::STICK, PersistentDataType.STRING, "starter")
val BLOCK_PACK: Key<String, String> = Key(::BLOCK_PACK, PersistentDataType.STRING, "starter")