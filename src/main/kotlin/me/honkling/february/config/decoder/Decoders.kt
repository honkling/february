package me.honkling.february.config.decoder

import cc.ekblad.toml.model.TomlValue
import cc.ekblad.toml.transcoding.TomlDecoder
import kotlin.reflect.KType

typealias Decoder = TomlDecoder.(KType, TomlValue) -> Any?