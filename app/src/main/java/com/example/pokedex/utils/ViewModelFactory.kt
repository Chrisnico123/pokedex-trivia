package com.example.pokedex.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.data.local.PokemonRepository
import com.example.pokedex.di.Injection
import com.example.pokedex.ui.favorite.FavoriteViewModel
import com.example.pokedex.ui.pokemon.MyPokemonViewModel

class ViewModelFactory private constructor(private val pokemonRepository: PokemonRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(pokemonRepository) as T
        }
        if (modelClass.isAssignableFrom(MyPokemonViewModel::class.java)) {
            return MyPokemonViewModel(pokemonRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}