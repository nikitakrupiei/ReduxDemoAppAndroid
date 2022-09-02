package com.nikita.krupiei.reduxdemoapp.screen.itemDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikita.krupiei.reduxdemoapp.core.AppState
import com.nikita.krupiei.reduxdemoapp.core.navigation.NavAction
import com.nikita.krupiei.reduxdemoapp.core.store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.rekotlin.StoreSubscriber

class ItemDetailsViewModel : ViewModel(), StoreSubscriber<AppState> {

    private val screenState = MutableStateFlow<ItemDetailsState>(store.state.itemDetailsState)
    val state = screenState.asStateFlow()

    override fun newState(state: AppState) {
        viewModelScope.launch {
            screenState.emit(state.itemDetailsState)
        }
    }

    fun onBackButtonClicked() {
        store.dispatch(NavAction.Back)
    }
}