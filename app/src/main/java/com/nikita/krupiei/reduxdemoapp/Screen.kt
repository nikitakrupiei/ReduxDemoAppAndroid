package com.nikita.krupiei.reduxdemoapp

import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.android.AppScreen
import com.nikita.krupiei.reduxdemoapp.screen.createItem.CreateItemFragmentDialog
import com.nikita.krupiei.reduxdemoapp.screen.itemDetails.ItemDetailsFragment
import com.nikita.krupiei.reduxdemoapp.screen.itemsList.ItemsListFragment
import kotlinx.parcelize.Parcelize

object Screen {
    @Parcelize
    object ItemsList : AppScreen("items_list") {
        override fun create(): Fragment = ItemsListFragment()
    }

    @Parcelize
    object ItemDetails : AppScreen("item_details") {
        override fun create(): Fragment = ItemDetailsFragment()
    }

    @Parcelize
    object CreateItem : ModalScreen("create_item") {
        override fun create(): DialogFragment = CreateItemFragmentDialog()
    }
}

abstract class ModalScreen(
    override val id: String
) : Screen, Parcelable {
    abstract fun create(): DialogFragment
}