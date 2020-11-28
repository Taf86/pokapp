package com.dc.pokapp.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.dc.pokapp.R
import kotlinx.android.synthetic.main.item_pokemon_load_state.view.*

class PokemonLoadStateViewHolder(
    parent: ViewGroup,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_load_state, parent, false)
) {
    fun bind(loadState: LoadState) = with(itemView) {
        progressBar.isVisible = loadState is LoadState.Loading
        errorTextView.apply {
            isVisible = loadState is LoadState.Error
            text = (loadState as? LoadState.Error)?.error?.localizedMessage
        }
        retryButton.apply {
            isVisible = loadState is LoadState.Error
            setOnClickListener {
                (loadState as? LoadState.Error)?.run { retry() }
            }
        }
        return@with
    }
}