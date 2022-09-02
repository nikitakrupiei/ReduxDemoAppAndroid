package com.nikita.krupiei.reduxdemoapp.screen.itemsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita.krupiei.reduxdemoapp.Screen
import com.nikita.krupiei.reduxdemoapp.core.navigation.NavAction
import com.nikita.krupiei.reduxdemoapp.core.AppState
import com.nikita.krupiei.reduxdemoapp.core.ShowModal
import com.nikita.krupiei.reduxdemoapp.core.store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.rekotlin.StoreSubscriber

class ItemsListViewModel : ViewModel(), StoreSubscriber<AppState> {

    private val screenState = MutableStateFlow<ItemsListState>(store.state.itemsListState)
    val state = screenState.asStateFlow()

    init {
        store.subscribe(this)
    }

    override fun newState(state: AppState) {
        viewModelScope.launch {
            screenState.emit(state.itemsListState)
        }
    }

    fun onAddBtnClicked() {
        store.dispatch(ShowModal(Screen.CreateItem))
    }

    fun onItemClicked(position: Int) {
        store.dispatch(OnItemClicked(store.state.itemsListState.dataItems[position]))
        store.dispatch(NavAction.Forward(Screen.ItemDetails))
    }
}