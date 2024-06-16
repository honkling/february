package me.honkling.february.command.types

import me.honkling.commando.common.generic.ICommandSender
import me.honkling.commando.common.types.Type
import me.honkling.february.stats.key.Key

object KeyType : Type<Key<*, *>> {
    private val keys = Class.forName("me.honkling.february.stats.key.KeysKt")
        .declaredFields
        .map {
            it.isAccessible = true
            it[null]
        }
        .filterIsInstance<Key<*, *>>()

    override fun complete(sender: ICommandSender<*>, input: String): List<String> {
        return keys
            .map { it.key.key }
            .filter { input in it }
    }

    override fun parse(sender: ICommandSender<*>, input: String): Pair<Key<*, *>, Int> {
        val first = input.split(" ")[0]
        return keys.find { it.key.key == first.lowercase() }!! to 1
    }

    override fun validate(sender: ICommandSender<*>, input: String): Boolean {
        val first = input.split(" ")[0]
        return keys.any { it.key.key == first.lowercase() }
    }
}