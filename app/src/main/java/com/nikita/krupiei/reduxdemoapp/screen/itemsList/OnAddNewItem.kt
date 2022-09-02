package com.nikita.krupiei.reduxdemoapp.screen.itemsList

import org.rekotlin.Action
import org.rekotlin.Reducer
import org.rekotlin.StateType

class OnAddNewItem(val item: String) : Action
class OnItemClicked(val item: String) : Action

data class ItemsListState(
    var dataItems: List<String> = emptyList()
) : StateType {

    companion object {
        fun reduce(): Reducer<ItemsListState> = { action: Action, state: ItemsListState? ->
            val itemsListState = state ?: ItemsListState()

            when (action) {
                is OnAddNewItem -> itemsListState.copy(dataItems = itemsListState.dataItems + action.item)
                else -> itemsListState
            }
        }
    }
}