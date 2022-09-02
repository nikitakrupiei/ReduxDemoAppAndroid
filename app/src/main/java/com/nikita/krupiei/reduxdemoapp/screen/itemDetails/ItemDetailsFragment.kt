package com.nikita.krupiei.reduxdemoapp.screen.itemDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikita.krupiei.reduxdemoapp.R
import com.nikita.krupiei.reduxdemoapp.core.collectWhenResumed
import com.nikita.krupiei.reduxdemoapp.databinding.FragmentItemDetailsBinding

class ItemDetailsFragment : Fragment(R.layout.fragment_item_details) {

    private val viewModel: ItemDetailsViewModel by viewModels()
    private lateinit var viewBinding: FragmentItemDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentItemDetailsBinding.bind(view)

        with(viewBinding.toolbar) {
            setNavigationIcon(R.drawable.ic_round_arrow_back_ios_24)
            setNavigationOnClickListener { viewModel.onBackButtonClicked() }
        }

        collectWhenResumed(viewModel.state) { state ->
            viewBinding.itemDetailsTextView.text = state.item
        }
    }
}