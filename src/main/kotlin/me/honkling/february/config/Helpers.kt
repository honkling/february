package me.honkling.february.config

import cc.ekblad.toml.model.TomlValue

fun <T> TomlValue.value(): T {
    return when (this) {
        is TomlValue.List -> elements
        is TomlValue.Map -> properties
        is TomlValue.Bool -> value
        is TomlValue.Double -> value
        is TomlValue.Integer -> value
        is TomlValue.LocalDate -> value
        is TomlValue.LocalDateTime -> value
        is TomlValue.LocalTime -> value
        is TomlValue.OffsetDateTime -> value
        is TomlValue.String -> value
    } as T
}