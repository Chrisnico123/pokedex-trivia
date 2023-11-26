package com.example.pokedex.data.remote.retrofit

import com.example.pokedex.data.remote.response.PokemonDetailResponse
import com.example.pokedex.data.remote.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun getListPokemon(@Query("page") page: Int): Call<PokemonResponse>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: String): Call<PokemonDetailResponse>
}