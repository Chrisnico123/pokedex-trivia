package com.example.pokedex.data.local

import androidx.lifecycle.LiveData
import com.example.pokedex.data.local.entity.Pokemon
import com.example.pokedex.data.local.room.PokemonDao

class PokemonRepository private constructor(
    private val pokemonDao: PokemonDao
) {

    fun getFavoritePokemon(userId: String): LiveData<List<Pokemon>> {
        return pokemonDao.getFavoritePokemons(userId)
    }

    fun getMyPokemon(userId: String): LiveData<List<Pokemon>> {
        return pokemonDao.getMyPokemons(userId)
    }

    suspend fun deletePokemon() = pokemonDao.deleteAll()

    suspend fun insertPokemonFavorite(pokemon: Pokemon){
        pokemonDao.insertPokemon(pokemon)
    }

    suspend fun insertMyPokemon(pokemon: Pokemon){
        pokemonDao.insertPokemon(pokemon)
    }

    suspend fun setPokemonFavorite(pokemon: Pokemon, favoriteState: Boolean) {
        pokemon.isFavorite = favoriteState
        pokemonDao.updatePokemon(pokemon)
    }

    suspend fun setMyPokemon(pokemon: Pokemon, catchState: Boolean) {
        pokemon.isMyPokemon = catchState
        pokemonDao.updatePokemon(pokemon)
    }

    companion object {
        @Volatile
        private var instance: PokemonRepository? = null
        fun getInstance(
            pokemonDao: PokemonDao
        ): PokemonRepository =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(pokemonDao)
            }.also { instance = it }
    }
}