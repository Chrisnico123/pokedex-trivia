package com.example.pokedex.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentMyPokemonBinding
import com.example.pokedex.utils.ViewModelFactory
import com.example.pokedex.adapter.MyPokemonAdapter
import com.google.firebase.auth.FirebaseAuth

class MyPokemonFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var _binding: FragmentMyPokemonBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyPokemonBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: MyPokemonViewModel by viewModels {
            factory
        }

        val pokemonAdapter = MyPokemonAdapter { pokemon ->
            if (pokemon.isMyPokemon){
                viewModel.deleteFromMyPokemon(pokemon)
            } else {
                viewModel.saveToMyPokemon(pokemon)
            }
        }

        val currentUser = firebaseAuth.currentUser

        currentUser?.uid?.let {
            viewModel.getMyPokemon(it).observe(viewLifecycleOwner, { myPokemon ->
                pokemonAdapter.submitList(myPokemon)
            })
        }

        binding?.rvListMyPokemon?.apply {
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