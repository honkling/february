package me.honkling.february.lib

import me.honkling.february.luckPerms
import net.kyori.adventure.text.Component
import net.luckperms.api.model.user.User
import net.luckperms.api.node.NodeType
import org.bukkit.entity.Player

val Player.lpUser: User
    get() = luckPerms.userManager.getUser(uniqueId)!!

fun Player.nameDisplay(): Component {
    val prefix = (lpUser.getNodes(NodeType.PREFIX)
        .maxByOrNull { it.priority }
        ?.metaValue?.plus(" ") ?: "<gray>")
        .mm()

    return prefix.append(name())
}