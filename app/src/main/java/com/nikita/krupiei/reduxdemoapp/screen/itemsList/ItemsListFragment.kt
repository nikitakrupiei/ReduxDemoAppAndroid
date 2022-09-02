package com.nikita.krupiei.reduxdemoapp.screen.itemsList

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikita.krupiei.reduxdemoapp.R
import com.nikita.krupiei.reduxdemoapp.core.collectWhenResumed
import com.nikita.krupiei.reduxdemoapp.databinding.FragmentListViewBinding

class ItemsListFragment : Fragment(R.layout.fragment_list_view) {

    private val viewModel: ItemsListViewModel by viewModels()
    private lateinit var viewBinding: FragmentListViewBinding
    private var adapter: ArrayAdapter<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentListViewBinding.bind(view)
        adapter = ArrayAdapter(requireContext(), R.layout.layout_list_item)
        setupView()
        subscribeForState()
    }

    private fun subscribeForState() {
        collectWhenResumed(viewModel.state) { mainState ->
            adapter?.insertListData(mainState.dataItems)
        }
    }

    private fun setupView() {
        with(viewBinding) {
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                viewModel.onItemClicked(position)
            }
            addBtn.setOnClickListener {
                viewModel.onAddBtnClicked()
            }
        }
    }

    private fun ArrayAdapter<String>.insertListData(data: List<String>) {
        clear()
        addAll(data)
        notifyDataSetChanged()
    }
}