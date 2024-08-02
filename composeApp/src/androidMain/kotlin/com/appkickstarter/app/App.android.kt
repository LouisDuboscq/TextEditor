package com.appkickstarter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.app.Application
import android.content.Context
import com.appkickstarter.app.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

lateinit var appContext: Context

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        appContext = this

        initKoin {
            modules(
                listOf(
                    module { androidContext(this@MainApplication) }
                )
            )
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

