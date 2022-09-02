package com.nikita.krupiei.reduxdemoapp.core

import android.app.Activity
import android.graphics.Rect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectWhenResumed(flow: Flow<T>, onCollect: (T) -> Unit) {
    lifecycleScope.launchWhenResumed {
        flow.collect { t ->
            onCollect.invoke(t)
        }
    }
}

fun Activity.getVisibleContentHeight(): Int {
    val rect = Rect()
    window?.decorView?.getWindowVisibleDisplayFrame(rect)
    return rect.height()
}
