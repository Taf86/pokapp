package com.dc.pokapp.view.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dc.pokapp.R
import com.dc.pokapp.state.AppState
import com.dc.pokapp.viewModel.AppViewModel
import io.uniflow.androidx.flow.onStates
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val appViewModel: AppViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.shared_transition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        onStates(appViewModel) { state ->
            when (state) {
                is AppState.Pokemon -> {

                    with(state.detail) {

                        toolbar.title = name

                        sprites.frontDefault?.let {
                            Glide.with(this@DetailFragment.requireContext())
                                .load(it)
                                .into(imageView)
                        }

                        statsTextView.text =
                            stats.joinToString("\n") { stat -> "- ${stat.stat.name}: ${stat.baseStat}" }


                        typesTextView.text =
                            types.joinToString("\n") { type -> "- ${type.type.name}" }
                    }
                }
            }

        }
    }


}