package com.example.pokedex.data.remote.response

import com.example.pokedex.model.DataItemPokemon
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("previous")
    val previous: String? = null,

    @field:SerializedName("results")
    val results: List<DataItemPokemon>? = null
)