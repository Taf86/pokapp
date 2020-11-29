package com.dc.pokapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val pokemonName: String,
    val prevKey: Int?,
    val nextKey: Int?
)