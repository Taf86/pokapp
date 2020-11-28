package com.dc.pokapp.view.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dc.pokapp.R
import com.dc.pokapp.event.ListEvent
import com.dc.pokapp.viewModel.ListViewModel
import io.uniflow.androidx.flow.onEvents
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val listViewModel: ListViewModel by viewModel()
    private val adapter = PokemonAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryButton.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                retryButton.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        retryButton.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = this.adapter.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter { this.adapter.retry() },
                footer = PokemonLoadStateAdapter { this.adapter.retry() }
            )
        }

        onEvents(listViewModel) { event ->
            when (val data = event.take()) {
                is ListEvent.SendPagingData -> lifecycleScope.launch {
                    adapter.submitData(data.pagingData)
                }
            }
        }


    }

}