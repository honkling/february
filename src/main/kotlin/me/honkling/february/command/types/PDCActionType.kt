package me.honkling.february.command.types

import me.honkling.commando.common.generic.ICommandSender
import me.honkling.commando.common.types.Type
import me.honkling.february.command.debug.PDCAction

object PDCActionType : Type<PDCAction> {
    override fun complete(sender: ICommandSender<*>, input: String): List<String> {
        return PDCAction.entries
            .map { it.name }
            .filter { input in it }
    }

    override fun parse(sender: ICommandSender<*>, input: String): Pair<PDCAction, Int> {
        val first = input.split(" ")[0]
        return PDCAction.valueOf(first) to 1
    }

    override fun validate(sender: ICommandSender<*>, input: String): Boolean {
        val first = input.split(" ")[0]
        return PDCAction.entries.any { it.name == first }
    }
}