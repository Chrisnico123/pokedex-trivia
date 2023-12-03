package com.example.pokedex.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.utils.ViewModelFactory
import com.example.pokedex.adapter.FavoriteAdapter
import com.google.firebase.auth.FirebaseAuth

class FavoriteFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        val pokemonAdapter = FavoriteAdapter { pokemon ->
            if (pokemon.isFavorite){
                viewModel.deleteFromFavorite(pokemon)
            } else {
                viewModel.saveToFavoritePokemon(pokemon)
            }
        }

        val currentUser = firebaseAuth.currentUser

        currentUser?.uid?.let {
            viewModel.getFavoritePokemon(it).observe(viewLifecycleOwner, { favoritePokemon ->
                pokemonAdapter.submitList(favoritePokemon)
            })
        }

        binding?.rvListFavPokemon?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}