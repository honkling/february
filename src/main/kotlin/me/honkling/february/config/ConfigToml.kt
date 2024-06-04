package me.honkling.february.config

import cc.ekblad.toml.decode
import cc.ekblad.toml.tomlMapper
import cc.ekblad.toml.util.InternalAPI
import me.honkling.february.config.decoder.itemStack
import me.honkling.february.config.decoder.location
import me.honkling.february.instance
import org.bukkit.Location
import org.bukkit.inventory.ItemStack

private val file = instance.dataFolder.resolve("config.toml")
var configToml = parseConfigToml()

data class ConfigToml(
    val spawn: Location,
    val sticks: Map<String, ItemStack>,
    val blocks: Map<String, BlockPack>
) {
    data class BlockPack(
        val primary: ItemStack,
        val secondary: ItemStack,
        val tertiary: ItemStack
    )
}

@OptIn(InternalAPI::class)
fun parseConfigToml(): ConfigToml {
    val mapper = tomlMapper {
        decoder(Location::class, location)
        decoder(ItemStack::class, itemStack)
    }

    if (!file.exists())
        instance.saveResource("config.toml", true)

    return mapper.decode<ConfigToml>(file.toPath())
}