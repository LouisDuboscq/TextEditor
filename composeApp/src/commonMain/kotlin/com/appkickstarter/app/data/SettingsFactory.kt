package com.appkickstarter.app.data

import com.russhwolf.settings.ObservableSettings

expect class SettingsFactory {
    fun createSettings(): ObservableSettings
}