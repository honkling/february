package me.honkling.february.task

import me.honkling.february.lib.players
import me.honkling.february.stats.profile

const val DROP_BOUND = 95
const val DEATH_BOUND = 45

class BoundTask : Task(1) {
    override fun execute() {
        for (player in players) {
            val location = player.location

            if (location.y <= DROP_BOUND && player.inventory.isEmpty)
                player.profile.refreshKit()

            if (location.y <= DEATH_BOUND && !player.inventory.isEmpty)
                player.health = 0.0
        }
    }
}