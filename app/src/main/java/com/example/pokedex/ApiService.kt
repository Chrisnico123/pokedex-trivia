package com.example.pokedex

import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun getListPokemon(@Query("page") page: String): Call<ResponseData>

    //get list user by id using path
    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: String): Call<Pokemon>
}