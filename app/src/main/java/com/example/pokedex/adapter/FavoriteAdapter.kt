package com.example.pokedex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.R
import com.example.pokedex.data.local.entity.Pokemon
import com.example.pokedex.databinding.ItemFavPokemonBinding
import com.example.pokedex.adapter.FavoriteAdapter.MyViewHolder

class FavoriteAdapter(private val onFavoriteClick: (Pokemon) -> Unit) : ListAdapter<Pokemon, MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
        val btnUnlike = holder.binding.btnUnlike

        btnUnlike.setOnClickListener{
            onFavoriteClick(pokemon)
        }
    }

    class MyViewHolder(val binding: ItemFavPokemonBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(pokemon: Pokemon) {
            binding.tvNamePokemon.text = pokemon.namaPokemon
            binding.tvCodePokemon.text = pokemon.codePokemon
            Glide.with(itemView.context)
                .load(pokemon.imagePokemon)
                .apply(RequestOptions.placeholderOf(R.drawable.bulbasaur).error(R.drawable.bulbasaur))
                .into(binding.ivSpritePokemon)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Pokemon> =
            object : DiffUtil.ItemCallback<Pokemon>() {
                override fun areItemsTheSame(oldUser: Pokemon, newUser: Pokemon): Boolean {
                    return oldUser.id == newUser.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: Pokemon, newUser: Pokemon): Boolean {
                    return oldUser == newUser
                }
            }
    }
}