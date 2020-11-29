package com.dc.pokapp.model

data class LocalPage<T>(
    val next: Int?,
    val previous: Int?,
    val results: List<T>
)