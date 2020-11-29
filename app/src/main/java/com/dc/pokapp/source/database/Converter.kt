package com.dc.pokapp.source.database

import androidx.room.TypeConverter
import com.dc.pokapp.model.StatValue
import com.dc.pokapp.model.TypeValue
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converter {
    @TypeConverter
    fun convertStats(value: List<StatValue>?): String? {
        return value?.let {
            Moshi.Builder().build().adapter<List<StatValue>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    StatValue::class.java
                )
            ).toJson(value)
        }
    }

    @TypeConverter
    fun convertStats(value: String?): List<StatValue> {
        return value?.let {
            Moshi.Builder().build().adapter<List<StatValue>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    StatValue::class.java
                )
            ).fromJson(value)
        } ?: listOf()
    }

    @TypeConverter
    fun convertTypes(value: List<TypeValue>?): String? {
        return value?.let {
            Moshi.Builder().build().adapter<List<TypeValue>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    TypeValue::class.java
                )
            ).toJson(value)
        }
    }

    @TypeConverter
    fun convertTypes(value: String?): List<TypeValue> {
        return value?.let {
            Moshi.Builder().build().adapter<List<TypeValue>>(
                Types.newParameterizedType(
                    MutableList::class.java,
                    TypeValue::class.java
                )
            ).fromJson(value)
        } ?: listOf()
    }
}