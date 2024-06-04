package me.honkling.february.stats.field

import me.honkling.february.instance
import kotlin.reflect.KProperty

class GlobalStatsField(name: String) {
    private val file = instance.dataFolder.resolve(name)
    private var value = if (file.exists()) file.readText().toInt()
    else 0

    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): Int {
        return value
    }

    operator fun setValue(thisRef: Nothing?, property: KProperty<*>, value: Int) {
        this.value = value
    }
}