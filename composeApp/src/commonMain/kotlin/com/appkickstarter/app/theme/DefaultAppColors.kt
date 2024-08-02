package com.appkickstarter.app.theme

import androidx.compose.ui.graphics.Color

val slate50 = Color(0XFFF8FAFC)
val slate200 = Color(0XFFE2E8F0)
val slate300 = Color(0XFFCBD5E1)
val slate400 = Color(0XFF94A3B8)
val slate500 = Color(0XFF64748B)
val slate600 = Color(0XFF475569)
val slate900 = Color(0XFF0F172A)

val DefaultAppColors: (Boolean) -> CustomColors = { useDarkTheme ->
    if (useDarkTheme) {
        CustomColors(
            background = slate600,
            cardBackground = Color(0XFF252736),
            textOnCard = Color(0XFFFEFEFE),
            textOnButton = Color(0xFFFFFFFC),
            textOnBackground = Color(0XFFFEFEFE),
            buttonBackground = Color(0XFF161415),
            backBackground = slate400,
            icon = slate400,
            useDarkIcons = false,
            activeIndicator = Color.Black,
            inactiveIndicator = Color.White
        )
    } else {
        CustomColors(
            textOnCard = Color(0XFF161415),
            textOnButton = slate50,
            textOnBackground = Color(0XFF161415),
            buttonBackground = slate900,
            backBackground = slate600,
            cardBackground = slate300,
            icon = slate500,
            background = slate200,
            useDarkIcons = true,
            activeIndicator = Color.Black,
            inactiveIndicator = Color.White
        )
    }
}
