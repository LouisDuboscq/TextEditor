package com.appkickstarter.app.root

import com.appkickstarter.app.di.initKoin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object AppInitializer {
    var initialized = false

    fun initialize(
        appDeclaration: KoinApplication.() -> Unit
    ) {
        println("initialize")
        if (!initialized) {
            println("startKoin")
            initKoin(appDeclaration)
            initialized = true
        }
    }
}
