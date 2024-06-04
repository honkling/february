package me.honkling.february.config.decoder

import cc.ekblad.toml.model.TomlValue
import me.honkling.february.config.value
import me.honkling.february.lib.mm
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.reflect.KType

val itemStack: Decoder = { type: KType, it: TomlValue ->
    if ((it !is TomlValue.Map || ("id" !in it.properties || "name" !in it.properties)) && it !is TomlValue.String)
        it
    else {
        if (it is TomlValue.Map) {
            val (properties) = it

            val material = Material.matchMaterial(properties["id"]!!.value())
                ?: throw IllegalArgumentException("Invalid item stack id '${properties["id"]}'")

            val itemStack = ItemStack(material)
            val itemMeta = itemStack.itemMeta
            itemMeta.displayName("<i:false><white>${properties["name"]!!.value<String>()}".mm())
            itemStack.itemMeta = itemMeta
            itemStack
        } else {
            val id = it.value<String>()
            val material = Material.matchMaterial(id)
                ?: throw IllegalArgumentException("Invalid item stack id '${id}'")

            ItemStack(material)
        }
    }
}