package me.honkling.february.stats.field

import me.honkling.february.stats.Profile
import me.honkling.february.stats.key.Key
import kotlin.reflect.KProperty

class KeyField<Z : Any>(val key: Key<*, Z>) {
    operator fun setValue(thisRef: Profile, property: KProperty<*>, value: Z) {
        key[thisRef.player] = value
    }

    operator fun getValue(thisRef: Profile, property: KProperty<*>): Z {
        return key[thisRef.player]
    }
}