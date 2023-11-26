package com.example.pokedex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.FragmentDetailPokemonBinding

class PokemonDetailFragment : Fragment() {

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
        }

        binding.ivArrowBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

}