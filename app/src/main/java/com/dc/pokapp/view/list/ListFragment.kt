package com.dc.pokapp.view.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dc.pokapp.R
import com.dc.pokapp.event.AppEvent
import com.dc.pokapp.viewModel.AppViewModel
import io.uniflow.androidx.flow.onEvents
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val appViewModel: AppViewModel by sharedViewModel()
    private val adapter =
        PokemonAdapter { pokemon -> appViewModel.openPokemonDetail(pokemon.name) }

    private val loadStateListener = { loadState: CombinedLoadStates ->

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
        Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryButton.setOnClickListener {
            adapter.retry()
        }



        recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.setHasFixedSize(true)
            it.adapter = this.adapter.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter { this.adapter.retry() },
                footer = PokemonLoadStateAdapter { this.adapter.retry() }
            )
        }

        onEvents(appViewModel) { event ->
            when (val data = event.take()) {
                is AppEvent.SendPagingData -> lifecycleScope.launch {
                    adapter.submitData(data.pagingData)
                }

                is AppEvent.DetailLoading -> {
                    progressBar.isVisible = true
                }

                is AppEvent.DetailError -> {
                    progressBar.isVisible = false
                    Toast.makeText(context, data.error.message, Toast.LENGTH_LONG).show()
                }

                is AppEvent.DetailReady -> {
                    progressBar.isVisible = false
                    findNavController().navigate(
                        ListFragmentDirections.actionListToDetail(),
                        FragmentNavigatorExtras(constraintLayout to constraintLayout.transitionName)
                    )
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        adapter.addLoadStateListener(loadStateListener)
    }

    override fun onPause() {
        super.onPause()

        adapter.removeLoadStateListener(loadStateListener)

    }
}