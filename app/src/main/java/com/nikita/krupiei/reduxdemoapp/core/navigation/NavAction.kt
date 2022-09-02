package com.nikita.krupiei.reduxdemoapp.core.navigation

import com.github.terrakok.modo.Screen
import org.rekotlin.Action

sealed class NavAction : Action {
    class Forward(val screen: Screen, vararg val screens: Screen) : NavAction()
    class Replace(val screen: Screen, vararg val screens: Screen) : NavAction()
    class NewStack(val screen: Screen, vararg val screens: Screen) : NavAction()
    class BackTo(val screenId: String) : NavAction()
    object BackToRoot : NavAction()
    object Back : NavAction()
    object Exit : NavAction()
}
