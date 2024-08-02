package com.appkickstarter.app.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.appkickstarter.app.persons.PersonRepository
import com.appkickstarter.app.persons.PersonDetailScreenModel
import com.appkickstarter.app.persons.PersonListScreenModel
import org.koin.compose.currentKoinScope
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun sharedPlatformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, sharedPlatformModule())
}

// for ios
fun initKoin() {
    initKoin {  }
}

val commonModule = module {
    singleOf(::PersonRepository)
    factory { (id: String) -> PersonDetailScreenModel(id, repository = get()) }
    factoryOf(::PersonListScreenModel)
}

// tmp fix : https://github.com/InsertKoinIO/koin/issues/1879#issuecomment-2132926268 use koinScreenModel of voyager when fixed
@Composable
public inline fun <reified T : ScreenModel> Screen.koinScreenModel(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null
): T {
    val st = parameters?.let { rememberUpdatedState(parameters) }
    val tag = remember(qualifier, scope) { qualifier?.value }
    return rememberScreenModel(tag = tag) {
        scope.get(qualifier, st?.value)
    }
}

// tmp fix : https://github.com/InsertKoinIO/koin/issues/1879#issuecomment-2132926268 use koinNavigatorScreenModel of voyager when fixed
@Composable
public inline fun <reified T : ScreenModel> Navigator.koinNavigatorScreenModel(
    qualifier: Qualifier? = null,
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null
): T {
    val st = parameters?.let { rememberUpdatedState(parameters) }
    val tag = remember(qualifier, scope) { qualifier?.value }
    return rememberNavigatorScreenModel(tag = tag) {
        scope.get(qualifier, st?.value)
    }
}