package com.nikita.krupiei.reduxdemoapp.screen.itemDetails

import com.nikita.krupiei.reduxdemoapp.screen.itemsList.OnItemClicked
import org.rekotlin.Action
import org.rekotlin.Reducer
import org.rekotlin.StateType

data class ItemDetailsState(
    val item: String? = null
) : StateType {

    companion object {
        fun reduce(): Reducer<ItemDetailsState> = { action: Action, oldState: ItemDetailsState? ->
            val state = oldState ?: ItemDetailsState("")

            when (action) {
                is OnItemClicked -> state.copy(item = action.item)
                else -> state
            }
        }
    }
}