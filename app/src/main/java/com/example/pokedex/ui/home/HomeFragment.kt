package com.example.pokedex.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.R
import com.example.pokedex.data.remote.response.PokemonResponse
import com.example.pokedex.data.remote.response.PokemonDetailResponse
import com.example.pokedex.data.remote.response.Sprites
import com.example.pokedex.data.remote.response.Other
import com.example.pokedex.data.remote.response.Home
import com.example.pokedex.data.remote.response.Stats
import com.example.pokedex.data.remote.response.Types
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val apiService = ApiConfig.getApiService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pokemon, container, false)
        recyclerView = view.findViewById(R.id.rv_list_pokemon)

        setupRecyclerView()

        fetchPokemonData()

        return view
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        adapter = PokemonAdapter(ArrayList()) { selectedItem ->
            navigateToDetailFragment(selectedItem)
        }
        recyclerView.adapter = adapter
    }

    private fun fetchPokemonData() {
        val page = 1
        apiService.getListPokemon(page).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.results?.let { dataItemList ->
                        val pokemonDataList = mutableListOf<PokemonDetailResponse>()
                        var fetchedPokemonCount = 0
                        for (dataItem in dataItemList) {
                            apiService.getPokemon(dataItem.name ?: "").enqueue(object : Callback<PokemonDetailResponse> {
                                override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {
                                    if (response.isSuccessful) {
                                        val pokemon = response.body()
                                        pokemon?.let {
                                            val frontDefaultUrl = it.sprites?.other?.home?.frontDefault
                                            val updatedPokemon = PokemonDetailResponse(
                                                name = it.name,
                                                order = it.order,
                                                sprites = Sprites(other = Other(
                                                    home = Home(
                                                        frontDefault = frontDefaultUrl
                                                    )
                                                ))
                                            )
                                            pokemonDataList.add(updatedPokemon)
                                            fetchedPokemonCount++
                                            if (fetchedPokemonCount == dataItemList.size) {
                                                adapter.updateData(pokemonDataList)
                                            }
                                        }
                                    } else {}
                                }
                                override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {}
                            })
                        }
                    }
                } else {}
            }
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {}
        })
    }

    private fun extractPokemonIdFromUrl(url: String?): Int {
        val segments = url?.split("/") ?: emptyList()
        return segments[segments.size - 2].toInt()
    }

    private fun navigateToDetailFragment(itemData: PokemonDetailResponse) {
        val bundle = Bundle().apply {
            putString("name", itemData.name)
            itemData.order?.let { putInt("order", it) }
            putString("sprite", itemData.sprites?.other?.home?.frontDefault)
        }

        val detailFragment = PokemonDetailFragment()
        detailFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

}

