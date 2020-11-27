package com.dc.pokapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Page<T>(
    @field:Json(name = "results")
    val results: List<T>
)