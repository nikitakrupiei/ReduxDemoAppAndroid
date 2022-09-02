package com.nikita.krupiei.reduxdemoapp

import android.app.Application
import com.github.terrakok.modo.*
import com.github.terrakok.modo.android.AppReducer

val modo: Modo
    get() = ReduxDemoApplication.modo

class ReduxDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        modo = Modo(if (BuildConfig.DEBUG) LogReducer(AppReducer(this)) else AppReducer(this))
    }

    companion object {
        lateinit var modo: Modo
    }
}