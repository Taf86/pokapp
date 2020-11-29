package com.dc.pokapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    @field:Json(name = "name")
    val name: String
)