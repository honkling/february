@file:Listener

package me.honkling.february.event

import io.papermc.paper.event.player.AsyncChatEvent
import me.honkling.commando.common.annotations.Listener
import me.honkling.february.lib.nameDisplay
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

private fun onChat(event: AsyncChatEvent) = event.renderer { player, display, message, audience ->
    val color = if (player.hasPermission("chat.white")) NamedTextColor.WHITE
                else NamedTextColor.GRAY

    player.nameDisplay()
        .append(Component.text(": ")
            .color(color)
            .append(message))
}