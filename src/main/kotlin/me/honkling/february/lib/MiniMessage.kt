package me.honkling.february.lib

import me.honkling.february.BRAND
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

val mm = MiniMessage.miniMessage()

fun String.mm(vararg placeholders: TagResolver): Component {
    return mm.deserialize(
        this,
        Placeholder.styling("positive", TextColor.color(0xBF, 0xFF, 0xC6)),
        Placeholder.styling("negative", TextColor.color(0xFF, 0x6E, 0x6E)),
        Placeholder.styling("p", NamedTextColor.DARK_AQUA),
        Placeholder.styling("s", NamedTextColor.AQUA),
        *placeholders)
}

fun Audience.sendBroadcast(message: String) {
    sendMiniMessage("<b><p>$BRAND</b> <dark_gray>|</dark_gray> $message")
}

fun Audience.sendPositive(message: String) {
    sendMiniMessage("<positive>✔</positive> <dark_gray>»</dark_gray> <gray>$message")
}

fun Audience.sendInfo(message: String) {
    sendMiniMessage("<b>!</b> <dark_gray>»</dark_gray> <gray>$message")
}

fun Audience.sendNegative(message: String) {
    sendMiniMessage("<negative>✖</negative> <dark_gray>»</dark_gray> <gray>$message")
}

private fun Audience.sendMiniMessage(message: String) {
    sendMessage(message.mm())
}