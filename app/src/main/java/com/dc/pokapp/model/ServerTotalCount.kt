package com.dc.pokapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_total_count")
data class ServerTotalCount(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 1,

    @ColumnInfo(name = "total_count")
    val totalCount: Int?
)