package me.honkling.february.config.decoder

import cc.ekblad.toml.model.TomlValue
import me.honkling.february.config.value
import org.bukkit.Bukkit
import org.bukkit.Location
import kotlin.reflect.KType

val location: Decoder = { type: KType, it: TomlValue ->
    if (it !is TomlValue.Map)
        it
    else {
        val (properties) = it

        if (!properties.keys.containsAll(listOf("x", "y", "z", "world")))
            it
        else
            Location(
                Bukkit.getWorld(properties["world"]!!.value<String>()),
                properties["x"]!!.value(),
                properties["y"]!!.value(),
                properties["z"]!!.value(),
                properties["yaw"]?.value<Double>()?.toFloat() ?: 0f,
                properties["pitch"]?.value<Double>()?.toFloat() ?: 0f
            )
    }
}