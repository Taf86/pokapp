package com.dc.pokapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetail(
    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "sprites")
    val sprites: Sprites,

    @field:Json(name = "stats")
    val stats: List<StatValue>,

    @field:Json(name = "types")
    val types: List<TypeValue>
)


@JsonClass(generateAdapter = true)
data class Sprites(
    @field:Json(name = "front_default")
    val frontDefault: String?
)


@JsonClass(generateAdapter = true)
data class Stat(
    @field:Json(name = "name")
    val name: String
)


@JsonClass(generateAdapter = true)
data class StatValue(
    @field:Json(name = "base_stat")
    val baseStat: Long,

    @field:Json(name = "stat")
    val stat: Stat
)

@JsonClass(generateAdapter = true)
data class Type(
    @field:Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class TypeValue(
    @field:Json(name = "type")
    val type: Type
)