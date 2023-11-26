package com.example.pokedex.data.remote.response

import com.example.pokedex.model.DataItemPokemon
import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("order")
    val order: Int? = null,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("stats")
    val stats: ArrayList<Stats>? = arrayListOf(),

    @field:SerializedName("types")
    val types: ArrayList<Types>? = arrayListOf()
)

//Sprites
data class Sprites(
    @field:SerializedName("other")
    val other: Other? = null
)

data class Other(
    @field:SerializedName("home")
    val home: Home? = null,
)

data class Home(
    @SerializedName("front_default")
    val frontDefault: String? = null,

    @SerializedName("front_female")
    val frontFemale: String? = null,

    @SerializedName("front_shiny")
    val frontShiny: String? = null,

    @SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null
)

//Stats
data class Stats(
    @field:SerializedName("base_stat")
    val baseStat: Int? = null,

    @field:SerializedName("effort")
    val effort: Int? = null,

    @field:SerializedName("stat")
    val stat: DataItemPokemon? = null
)

//Types
data class Types(
    @field:SerializedName("slot")
    val slot: Int? = null,

    @field:SerializedName("type")
    val type: DataItemPokemon? = null
)
