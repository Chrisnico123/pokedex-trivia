package com.example.pokedex.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.pokedex.data.local.entity.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons where user_id = :userId AND favorited = 1")
    fun getFavoritePokemons(userId: String): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemons where user_id = :userId AND mypokemon = 1")
    fun getMyPokemons(userId: String): LiveData<List<Pokemon>>

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)

    @Update
    suspend fun updatePokemon(pokemon: Pokemon)

    @Query("DELETE FROM pokemons WHERE mypokemon = 0")
    suspend fun deleteAll()

}