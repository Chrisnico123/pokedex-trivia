package com.example.pokedex.ui.home

import android.os.Bundle
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.FragmentDetailPokemonBinding
import com.example.pokedex.utils.ViewModelFactory
import com.example.pokedex.ui.favorite.FavoriteViewModel
import com.example.pokedex.data.local.entity.Pokemon
import com.example.pokedex.ui.pokemon.MyPokemonViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

class PokemonDetailFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var _binding: FragmentDetailPokemonBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var name = "name"
        var order = "order"
        var sprite = "sprite"
        var stats = "stats"
        var types = "types"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        val viewModel2: MyPokemonViewModel by viewModels {
            factory
        }

        if (arguments != null) {
            val name = arguments?.getString(name)
            val order = arguments?.getInt(order)
            val sprite = arguments?.getString(sprite)
            binding.tvNamePokemon.text = name
            binding.tvCodePokemon.text = "#${order}"
            Glide.with(this)
                .load(sprite)
                .placeholder(R.drawable.bulbasaur)
                .error(R.drawable.bulbasaur)
                .into(binding.ivSpritePokemon)

            val isFavorite = true
            val isMyPokemon = false
            val currentUser = firebaseAuth.currentUser
            val userId = currentUser?.uid
            val id = getRandomId()

            val pokemonFavoriteData = Pokemon(
                id,
                name,
                "#${order}",
                sprite,
                isFavorite,
                isMyPokemon,
                userId
            )

            val myPokemonData = Pokemon(
                id,
                name,
                "#${order}",
                sprite,
                !isFavorite,
                !isMyPokemon,
                userId
            )

            binding.btnLike.setOnClickListener{
//                viewModel.deletePokemonAll()
                viewModel.insertFavoritePokemon(pokemonFavoriteData)
                Toast.makeText(activity, "Success Add to Favorite", Toast.LENGTH_SHORT).show()
            }

            binding.btnCatch.setOnClickListener{
                val randomNumber = generateRandomInteger(1,5)
                println(randomNumber)
                if(randomNumber == 3){
                    viewModel2.insertMyPokemon(myPokemonData)
                    Toast.makeText(activity, "Success Catch Pokemon", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity, "Failed to Catch, Try Again!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ivArrowBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

    private fun generateRandomInteger(minValue: Int, maxValue: Int): Int {
        return Random.nextInt(minValue, maxValue + 1)
    }

    private fun getRandomId(): Int {
        return Random.nextInt()
    }

}