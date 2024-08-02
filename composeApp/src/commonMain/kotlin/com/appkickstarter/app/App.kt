package com.appkickstarter.app

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.voyager.navigator.Navigator
import com.appkickstarter.app.onboarding.OnboardingScreenContent
import com.appkickstarter.app.persons.PersonsListScreen
import com.appkickstarter.app.theme.KickstarterTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun App() = KickstarterTheme(
    selectedTheme = "default",
    selectedThemeBrightness = "system"
) {
    var showOnboarding by rememberSaveable { mutableStateOf(true) }
    
    if (showOnboarding) OnboardingScreenContent {
        showOnboarding = false
    } else {
        Navigator(PersonsListScreen)
    }
}

@Preview
@Composable
fun App_Preview() { App() }