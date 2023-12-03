package com.example.pokedex.di

import android.content.Context
import com.example.pokedex.data.local.PokemonRepository
import com.example.pokedex.data.local.room.PokemonDatabase

object Injection {
    fun provideRepository(context: Context): PokemonRepository {
        val database = PokemonDatabase.getInstance(context)
        val dao = database.pokemonDao()
        return PokemonRepository.getInstance(dao)
    }
}