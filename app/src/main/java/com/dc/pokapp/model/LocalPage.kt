package com.dc.pokapp.model

data class LocalPage(
    val total: Int = 0,
    val items: List<Pokemon> = emptyList(),
    val nextPage: Int? = null
)