@file:Listener

package me.honkling.february.event

import fr.mrmicky.fastboard.adventure.FastBoard
import me.honkling.commando.common.annotations.Listener
import me.honkling.february.BRAND
import me.honkling.february.lib.mm
import me.honkling.february.lib.players
import me.honkling.february.stats.joins
import me.honkling.february.stats.profile
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

private val boards = mutableMapOf<UUID, FastBoard>()

fun updateBoard(player: Player) {
    val uuid = player.uniqueId
    val board = boards[uuid] ?: FastBoard(player)
    boards[uuid] = board

    val profile = player.profile
    val kills = player.getStatistic(Statistic.PLAYER_KILLS)
    val deaths = player.getStatistic(Statistic.DEATHS)

    board.updateTitle("<b><dark_aqua>$BRAND</b> <dark_gray>| <b><aqua>S1".mm())
    board.updateLines(
        Component.empty(),
        "<b><aqua>KFFA".mm(),
        formatStat("Kills", kills),
        formatStat("Deaths", deaths),
        formatStat("KDR", kills / deaths.toFloat()),
        formatStat("Streak", "${profile.streak} <b><dark_gray>(<b:false><aqua>${profile.bestStreak}</aqua></b>)"),
        Component.empty(),
        "<b><aqua>SERVER".mm(),
        formatStat("Online", players.size),
        formatStat("Joins", joins),
        formatStat("TPS", String.format("%.2f", Bukkit.getTPS()[0])),
        Component.empty(),
        "<i><gray>$BRAND.minehut.gg".mm()
    )
}

private fun onJoin(event: PlayerJoinEvent) {
    players.forEach(::updateBoard)
}

private fun onQuit(event: PlayerQuitEvent) {
    boards.remove(event.player.uniqueId)?.delete()
}

private fun formatStat(stat: String, value: Any): Component {
    return "<dark_gray>●</dark_gray> <gray>$stat:</gray> <white>$value</white>".mm()
}