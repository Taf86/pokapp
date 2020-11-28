package com.dc.pokapp.view.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dc.pokapp.model.Pokemon

class PokemonAdapter(private val onItemSelected: (pokemon: Pokemon) -> Unit) :
    PagingDataAdapter<Pokemon, PokemonViewHolder>(PokemonComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        return PokemonViewHolder(parent) { pokemon -> onItemSelected(pokemon) }
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        getItem(position)?.let { holder.bindPokemon(it) } ?: Unit


    object PokemonComparator : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem
    }
}