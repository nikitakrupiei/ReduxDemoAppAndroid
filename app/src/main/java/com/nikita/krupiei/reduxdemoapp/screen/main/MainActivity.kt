package com.nikita.krupiei.reduxdemoapp.screen.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.ModoRender
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.nikita.krupiei.reduxdemoapp.R
import com.nikita.krupiei.reduxdemoapp.Screen
import com.nikita.krupiei.reduxdemoapp.core.AppState
import com.nikita.krupiei.reduxdemoapp.core.navigation.NavAction
import com.nikita.krupiei.reduxdemoapp.core.store
import com.nikita.krupiei.reduxdemoapp.modo
import kotlinx.coroutines.delay
import org.rekotlin.StoreSubscriber

class MainActivity : AppCompatActivity(), StoreSubscriber<AppState> {

    private val modoRenderer by lazy {
        object : ModoRender(this@MainActivity, R.id.fragment_container_view) {
            override fun setupTransaction(
                fragmentManager: FragmentManager,
                transaction: FragmentTransaction,
                screen: AppScreen,
                newFragment: Fragment
            ) {
                transaction.setReorderingAllowed(true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        store.subscribe(this)

        lifecycleScope.launchWhenResumed {
            delay(1000)
            window.decorView.background = ColorDrawable(Color.WHITE)
            store.dispatch(NavAction.Replace(Screen.ItemsList))
        }

    }

    override fun onResume() {
        modo.render = modoRenderer
        super.onResume()
    }

    override fun onPause() {
        modo.render = null
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        modo.saveState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        modo.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        modo.back()
    }

    override fun newState(state: AppState) {
        state.modalState?.screen?.let { screen ->
            screen.create().show(supportFragmentManager, screen.id)
        }
    }
}