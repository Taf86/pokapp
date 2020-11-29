package com.dc.pokapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Page<T>(

    @field:Json(name = "next")
    val next: String?,

    @field:Json(name = "previous")
    val previous: String?,

    @field:Json(name = "results")
    val results: List<T>
)