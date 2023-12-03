package com.example.pokedex.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.local.PokemonRepository
import com.example.pokedex.data.local.entity.Pokemon
import kotlinx.coroutines.launch
import retrofit2.http.*

class FavoriteViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    fun getFavoritePokemon(userId: String) = pokemonRepository.getFavoritePokemon(userId)

    fun insertFavoritePokemon(pokemon: Pokemon){
        viewModelScope.launch {
            pokemonRepository.insertPokemonFavorite(pokemon)
        }
    }

    fun deletePokemonAll(){
        viewModelScope.launch {
            pokemonRepository.deletePokemon()
        }
    }

    fun saveToFavoritePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.setPokemonFavorite(pokemon, true)
        }
    }

    fun deleteFromFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.setPokemonFavorite(pokemon, false)
        }
    }
}
