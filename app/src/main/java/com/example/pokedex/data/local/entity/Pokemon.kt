package com.example.pokedex.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
class Pokemon(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @field:ColumnInfo(name = "nama_pokemon")
    val namaPokemon: String? = null,

    @field:ColumnInfo(name = "code_pokemon")
    val codePokemon: String? = null,

    @field:ColumnInfo(name = "image_pokemon")
    val imagePokemon: String? = null,

    @field:ColumnInfo(name = "favorited")
    var isFavorite: Boolean,

    @field:ColumnInfo(name = "mypokemon")
    var isMyPokemon: Boolean,

    @field:ColumnInfo(name = "user_id")
    var userId: String? = null
)