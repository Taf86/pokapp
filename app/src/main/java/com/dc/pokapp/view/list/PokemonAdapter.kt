package com.dc.pokapp.view.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dc.pokapp.model.Pokemon

class PokemonAdapter : PagingDataAdapter<Pokemon, PokemonViewHolder>(PokemonComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        return PokemonViewHolder(parent) {}
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPokemon(it) }
    }

    object PokemonComparator : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
}