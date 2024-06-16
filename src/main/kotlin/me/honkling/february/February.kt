package me.honkling.february

import me.honkling.commando.common.getClassesInPackage
import me.honkling.commando.spigot.SpigotCommandManager
import me.honkling.commando.spigot.SpigotListenerManager
import me.honkling.february.command.debug.PDCAction
import me.honkling.february.command.types.KeyType
import me.honkling.february.command.types.PDCActionType
import me.honkling.february.stats.key.Key
import me.honkling.february.task.Task
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

const val BRAND = "V2Flexed"

val instance = JavaPlugin.getPlugin(February::class.java)
val logger = instance.logger

val luckPerms = LuckPermsProvider.get()
val scheduler = Bukkit.getScheduler()

class February : JavaPlugin() {
    override fun onEnable() {
        setupCommando()
        scheduleTasks()

        logger.info("February is enabled.")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    private fun scheduleTasks() {
        val tasks = getClassesInPackage(this::class.java, "me.honkling.february.task")

        for (task in tasks) {
            if (!Task::class.java.isAssignableFrom(task) || task == Task::class.java)
                continue

            val instance = task.getConstructor().newInstance() as Task
            scheduler.scheduleSyncRepeatingTask(this, instance::execute, 0L, instance.repeatEvery.toLong())
        }
    }

    private fun setupCommando() {
        val commands = SpigotCommandManager(this)
        val listeners = SpigotListenerManager(this)

        commands.types[Key::class.java] = KeyType
        commands.types[PDCAction::class.java] = PDCActionType

        commands.registerCommands("me.honkling.february.command")
        listeners.registerListeners("me.honkling.february.event")
    }
}
