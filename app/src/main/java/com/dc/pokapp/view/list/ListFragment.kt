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
import com.dc.pokapp.R
import com.dc.pokapp.event.AppEvent
import com.dc.pokapp.viewModel.AppViewModel
import io.uniflow.androidx.flow.onEvents
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val appViewModel: AppViewModel by sharedViewModel()
    private val adapter = PokemonAdapter { pokemon ->
        appViewModel.openPokemonDetail(pokemon.name)
    }

    private val loadStateListener = { loadState: CombinedLoadStates ->
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
        errorState?.let {
            Toast.makeText(
                requireContext(),
                "\uD83D\uDE28 Wooops ${it.error}",
                Toast.LENGTH_LONG
            ).show()
        }
        Unit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.refresh) {
                appViewModel.search()
            }
            menuItem.itemId == R.id.refresh
        }

        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PokemonLoadStateAdapter { adapter.retry() },
            footer = PokemonLoadStateAdapter { adapter.retry() }
        )
        retryButton.setOnClickListener { adapter.retry() }

        onEvents(appViewModel) { event ->
            when (val data = event.take()) {
                is AppEvent.SendPagingData -> lifecycleScope.launch {
                    adapter.submitData(data.pagingData)
                }

                is AppEvent.DetailError -> {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${data.error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is AppEvent.DetailReady -> {
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