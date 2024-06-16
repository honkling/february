package me.honkling.february.command

import me.honkling.commando.common.annotations.Command
import me.honkling.february.lib.sendNegative
import me.honkling.february.lib.sendPositive
import me.honkling.february.stats.key.Key
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

@Command("debug", permission = "february.debug")
private object Debug {
    @Command("pdc")
    object PDC {
        @JvmStatic
        fun get(sender: CommandSender, player: Player, key: Key<*, *>) {
            if (player !in key)
                return sender.sendNegative("That player doesn't have the associated key.")

            val value = key[player]
            sender.sendPositive("The value attached to <white>${key.key.key}</white> for <white>${player.name}</white> is <white>$value</white>.")
        }

        @JvmStatic
        fun <T : Any> set(sender: CommandSender, player: Player, key: Key<T, T>, vararg input: String) {
            val joinedInput = input.joinToString(" ")
            val value = parseInput(key.type, joinedInput, player.persistentDataContainer.adapterContext)
                ?: return sender.sendNegative("That value is invalid.")

            key[player] = value
            sender.sendPositive("Set value of <white>${key.key.key}</white> for <white>${player.name}</white> to <white>$value</white>.")
        }

        @JvmStatic
        fun remove(sender: CommandSender, player: Player, key: Key<*, *>) {
            key.delete(player)
            sender.sendPositive("Deleted key <white>${key.key.key}</white> for <white>${player.name}</white>.")
        }
    }
}

private fun <T> parseInput(type: PersistentDataType<T, T>, input: String, context: PersistentDataAdapterContext): T? {
    val primitiveInput = when (type.primitiveType) {
        Byte::class.java -> input.toByte()
        java.lang.Byte::class.java -> input.toByte()
        java.lang.Short::class.java -> input.toShort()
        Short::class.java -> input.toShort()
        java.lang.Integer::class.java -> input.toInt()
        Int::class.java -> input.toInt()
        java.lang.Long::class.java -> input.toLong()
        Long::class.java -> input.toLong()
        java.lang.Float::class.java -> input.toFloat()
        Float::class.java -> input.toFloat()
        java.lang.Double::class.java -> input.toDouble()
        Double::class.java -> input.toDouble()
        java.lang.String::class.java -> input
        String::class.java -> input
        else -> null
    } as T?

    if (primitiveInput == null)
        return null

    return type.fromPrimitive(primitiveInput, context)
}