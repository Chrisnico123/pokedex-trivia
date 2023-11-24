package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("previous")
    val previous: String? = null,

    @field:SerializedName("results")
    val results: List<DataItem>? = null
)