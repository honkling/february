package me.honkling.february.task

import me.honkling.february.event.updateBoard
import me.honkling.february.lib.players

class SidebarTask : Task(20) {
    override fun execute() {
        for (player in players)
            updateBoard(player)
    }
}