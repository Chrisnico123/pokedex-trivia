package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class DataItem(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)