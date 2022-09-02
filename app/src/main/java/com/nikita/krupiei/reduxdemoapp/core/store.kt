package com.nikita.krupiei.reduxdemoapp.core

import org.rekotlin.Store

val store = Store(
    reducer = AppState.reduceAppState(),
    state = null
)