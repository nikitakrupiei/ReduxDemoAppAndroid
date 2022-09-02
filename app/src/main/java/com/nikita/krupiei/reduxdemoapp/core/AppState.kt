package com.nikita.krupiei.reduxdemoapp.core

import com.github.terrakok.modo.*
import com.nikita.krupiei.reduxdemoapp.ModalScreen
import com.nikita.krupiei.reduxdemoapp.core.navigation.NavAction
import com.nikita.krupiei.reduxdemoapp.modo
import com.nikita.krupiei.reduxdemoapp.screen.itemDetails.ItemDetailsState
import com.nikita.krupiei.reduxdemoapp.screen.itemsList.ItemsListState
import org.rekotlin.Action
import org.rekotlin.Reducer
import org.rekotlin.StateType

class ShowModal(val screen: ModalScreen) : Action
class OnDestroyModal(val screenId: String) : Action

data class ModalState(
    val screen: ModalScreen
)

data class AppState(
    val navigationState: NavigationState,
    val modalState: ModalState?,
    val itemsListState: ItemsListState,
    val itemDetailsState: ItemDetailsState,
) : StateType {
    companion object {
        fun reduceAppState(): Reducer<AppState> = { action, oldState ->
            val state = oldState ?: AppState(
                itemsListState = ItemsListState(),
                modalState = null,
                itemDetailsState = ItemDetailsState(),
                navigationState = NavigationState()
            )

            state.copy(
                itemsListState = ItemsListState.reduce().invoke(action, state.itemsListState),
                itemDetailsState = ItemDetailsState.reduce().invoke(action, state.itemDetailsState),
                navigationState = navigationReducer(action as? NavAction),
                modalState = modalReducer(action, state.modalState)
            )
        }

        private fun modalReducer(action: Action, oldState: ModalState?): ModalState? =
            when {
                action is ShowModal && oldState?.screen?.id != action.screen.id -> ModalState(action.screen)
                action is OnDestroyModal && oldState?.screen?.id == action.screenId -> null
                else -> oldState
            }

        private fun navigationReducer(
            action: NavAction?,
        ): NavigationState {
            when (action) {
                NavAction.Back -> modo.back()
                NavAction.BackToRoot -> modo.backToRoot()
                NavAction.Exit -> modo.exit()
                is NavAction.BackTo -> modo.backTo(action.screenId)
                is NavAction.Forward -> modo.forward(action.screen, *action.screens)
                is NavAction.NewStack -> modo.newStack(action.screen, *action.screens)
                is NavAction.Replace -> modo.replace(action.screen, *action.screens)
                else -> {
                    // no-op
                }
            }
            return modo.state
        }
    }
}



