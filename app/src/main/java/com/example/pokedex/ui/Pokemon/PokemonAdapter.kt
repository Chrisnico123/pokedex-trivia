package com.example.pokedex.ui.Pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon

class PokemonAdapter(private val pokemonList: MutableList<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPokemon: ImageView = itemView.findViewById(R.id.iv_pokemon)
        val textViewName: TextView = itemView.findViewById(R.id.tv_name_pokemon)
        val textViewIndex: TextView = itemView.findViewById(R.id.tv_code_pokemon)
    }

    fun updateData(newPokemonList: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        println(currentItem)

        Glide.with(holder.itemView.context)
            .load(currentItem.sprites?.other?.home?.frontDefault)
            .placeholder(R.drawable.bulbasaur)
            .error(R.drawable.bulbasaur)
            .into(holder.imageViewPokemon)

        holder.textViewName.text = currentItem.name ?: "Pokemon Name"
        holder.textViewIndex.text = "#${currentItem.order ?: "001"}"
    }

    override fun getItemCount() = pokemonList.size
}

