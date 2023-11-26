package com.example.pokedex.ui.Pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.model.ResponseData
import com.example.pokedex.model.HomeSprites
import com.example.pokedex.model.OtherSprites
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.SpriteDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val apiService = ApiConfig.getApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)

        setupRecyclerView()

        fetchPokemonData()

        return view
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        adapter = PokemonAdapter(ArrayList())
        recyclerView.adapter = adapter
    }

    private fun fetchPokemonData() {
        val page = 1
        apiService.getListPokemon(page).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.results?.let { dataItemList ->
                        val pokemonDataList = mutableListOf<Pokemon>()
                        var fetchedPokemonCount = 0 // Untuk melacak jumlah Pokemon yang sudah diambil
                        for (dataItem in dataItemList) {
                            apiService.getPokemon(dataItem.name ?: "").enqueue(object : Callback<Pokemon> {
                                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                                    if (response.isSuccessful) {
                                        val pokemon = response.body()
                                        pokemon?.let {
                                            val frontDefaultUrl = it.sprites?.other?.home?.frontDefault
                                            val updatedPokemon = Pokemon(
                                                name = it.name,
                                                order = it.order,
                                                sprites = SpriteDetails(other = OtherSprites(
                                                    home = HomeSprites(
                                                        frontDefault = frontDefaultUrl
                                                    )
                                                ))
                                            )
                                            pokemonDataList.add(updatedPokemon)
                                            fetchedPokemonCount++
                                            // Jika semua Pokemon sudah diambil, update adapter dengan data yang sudah dikumpulkan
                                            if (fetchedPokemonCount == dataItemList.size) {
                                                adapter.updateData(pokemonDataList)
                                            }
                                        }
                                    } else {
                                        // Handle response error
                                    }
                                }

                                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                                    // Handle network failure
                                }
                            })
                        }
                    }
                } else {
                    // Handle response error
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                // Handle network failure
            }
        })
    }

    private fun extractPokemonIdFromUrl(url: String?): Int {
        val segments = url?.split("/") ?: emptyList()
        return segments[segments.size - 2].toInt()
    }

}

