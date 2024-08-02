package com.appkickstarter.app.theme

import androidx.compose.ui.graphics.Color 

val slate50 = Color(0XFFF8FAFC)
val slate100 = Color(0XFFF1F5F9)
val slate200 = Color(0XFFE2E8F0)
val slate300 = Color(0XFFCBD5E1)
val slate400 = Color(0XFF94A3B8)
val slate500 = Color(0XFF64748B)
val slate600 = Color(0XFF475569)
val slate700 = Color(0XFF334155)
val slate800 = Color(0XFF1E293B)
val slate900 = Color(0XFF0F172A)

val DefaultAppColors: (Boolean) -> CustomColors = { useDarkTheme ->
    if (useDarkTheme) {
        CustomColors(
            background = slate600,
            cardBackground = Color(0XFF252736),
            sheetBackground = Color(0XFFF5F6F8),
            selectedIconBottomBar = slate50,
            unselectedIconBottomBar = slate300,
            textOnCard = Color(0XFFFEFEFE),
            textOnButton = Color(0xFFFFFFFC),
            textOnBackground = Color(0XFFFEFEFE),
            subtitle = Color(0XFF8E8FA1),
            buttonBackground = Color(0XFF161415),
            bottomBarBackground = Color(0XFF14121d),
            backIcon = slate400,
            backBackground = slate400,
            icon = slate400,
            selectedCardBackground = Color(0XFF241e34).copy(alpha = 0.7f),
            customCardBorder = Color.Black,
            badgeBackground = slate500,
            useDarkIcons = false,
            textFieldBackground = slate50,
            backgroundList = slate900,
            textOnBadge = slate50,
            bottomBarIndicatorColor = slate500,
        )
    } else {
        CustomColors(
            sheetBackground = Color(0XFFF5F6F8),
            unselectedIconBottomBar = slate500,
            selectedIconBottomBar = slate900,
            textOnCard = Color(0XFF161415),
            textOnButton = slate50,
            textOnBackground = Color(0XFF161415),
            subtitle = Color(0XFF5E6273),
            buttonBackground = slate900,
            bottomBarBackground = slate100,
            backIcon = slate500,
            backBackground = slate600,
            cardBackground = slate300,
            icon = slate500,
            background = slate200,
            selectedCardBackground = slate200,
            customCardBorder = slate50,
            badgeBackground = slate50,
            useDarkIcons = true,
            textFieldBackground = slate50,
            backgroundList = slate200,
            textOnBadge = slate900,
            bottomBarIndicatorColor = slate300,
        )
    }
}
