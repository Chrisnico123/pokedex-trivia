package com.example.pokedex.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.local.PokemonRepository
import com.example.pokedex.data.local.entity.Pokemon
import kotlinx.coroutines.launch
import retrofit2.http.*

class MyPokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    fun getMyPokemon(userId: String) = pokemonRepository.getMyPokemon(userId)

    fun insertMyPokemon(pokemon: Pokemon){
        viewModelScope.launch {
            pokemonRepository.insertMyPokemon(pokemon)
        }
    }

    fun deletePokemonAll(){
        viewModelScope.launch {
            pokemonRepository.deletePokemon()
        }
    }

    fun saveToMyPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.setMyPokemon(pokemon, true)
        }
    }

    fun deleteFromMyPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.setMyPokemon(pokemon, false)
        }
    }
}
