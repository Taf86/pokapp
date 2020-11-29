package com.dc.pokapp.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "pokemon_detail")
@JsonClass(generateAdapter = true)
data class PokemonDetail(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    @field:Json(name = "name")
    val name: String,

    @Embedded(prefix = "sprites_")
    @field:Json(name = "sprites")
    val sprites: Sprites,

    @ColumnInfo(name = "stats")
    @field:Json(name = "stats")
    val stats: List<StatValue>,

    @ColumnInfo(name = "types")
    @field:Json(name = "types")
    val types: List<TypeValue>
)


@JsonClass(generateAdapter = true)
data class Sprites(
    @ColumnInfo(name = "frontDefault")
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