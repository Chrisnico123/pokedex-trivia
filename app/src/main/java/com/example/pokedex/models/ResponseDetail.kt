package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("order")
    val order: Int? = null,

    @SerializedName("sprites")
    val sprites: SpriteDetails? = null
)

data class SpriteDetails(

    @SerializedName("other")
    val other: OtherSprites? = null
)

data class OtherSprites(
    @SerializedName("home")
    val home: HomeSprites? = null
)

data class HomeSprites(
    @SerializedName("front_default")
    val frontDefault: String? = null,

    @SerializedName("front_female")
    val frontFemale: String? = null,

    @SerializedName("front_shiny")
    val frontShiny: String? = null,

    @SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null
)
