package com.dc.pokapp.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dc.pokapp.R
import com.dc.pokapp.model.Pokemon
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonViewHolder(
    parent: ViewGroup,
    private val onItemSelected: (pokemon: Pokemon) -> Unit
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
    ) {
    fun bindPokemon(item: Pokemon) = with(itemView) {
        nameTextView.text = item.name
        constraintLayout.setOnClickListener { onItemSelected(item) }
    }
}