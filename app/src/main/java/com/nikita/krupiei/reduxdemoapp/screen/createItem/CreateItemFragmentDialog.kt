package com.nikita.krupiei.reduxdemoapp.screen.createItem

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nikita.krupiei.reduxdemoapp.Screen
import com.nikita.krupiei.reduxdemoapp.core.OnDestroyModal
import com.nikita.krupiei.reduxdemoapp.core.collectWhenResumed
import com.nikita.krupiei.reduxdemoapp.core.getVisibleContentHeight
import com.nikita.krupiei.reduxdemoapp.core.store
import com.nikita.krupiei.reduxdemoapp.databinding.FragmentCreateItemBinding
import com.nikita.krupiei.reduxdemoapp.screen.itemsList.OnAddNewItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateItemFragmentDialog : BottomSheetDialogFragment() {

    private val textFlow = MutableStateFlow<String?>(null)
    private lateinit var viewBinding: FragmentCreateItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCreateItemBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener { it.setupBottomSheet() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.inputLayout.editText?.doOnTextChanged { text, start, before, count ->
            lifecycleScope.launch {
                textFlow.emit(text?.toString())
            }
        }

        viewBinding.btnCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        viewBinding.btnCreate.setOnClickListener {
            val item = textFlow.value
            if (!item.isNullOrEmpty()) {
                store.dispatch(OnDestroyModal(Screen.CreateItem.id))
                store.dispatch(OnAddNewItem(item))
                dismissAllowingStateLoss()
            }
        }

        collectWhenResumed(textFlow) {
            viewBinding.btnCreate.isEnabled = !it.isNullOrEmpty()
        }
    }

    override fun onDestroy() {
        store.dispatch(OnDestroyModal(Screen.CreateItem.id))
        super.onDestroy()
    }

    private fun DialogInterface.setupBottomSheet() {
        val bottomSheetDialog = this as BottomSheetDialog
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            R.id.design_bottom_sheet
        ) ?: return

        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val contentHeight = activity?.getVisibleContentHeight() ?: 0
        if (contentHeight == 0) return
        if (layoutParams != null) {
            layoutParams.height = contentHeight
            behavior.peekHeight = contentHeight
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isFitToContents = false
        }
    }

}