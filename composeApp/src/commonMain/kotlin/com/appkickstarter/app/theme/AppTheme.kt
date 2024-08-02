package com.appkickstarter.app.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appkickstarter.composeapp.generated.resources.PlayfairDisplay_Bold
import appkickstarter.composeapp.generated.resources.Res.font
import appkickstarter.composeapp.generated.resources.RobotoCondensed_Medium
import org.jetbrains.compose.resources.Font

@Immutable
data class CustomColors(
    val useDarkIcons: Boolean,
    val background: Color,
    val buttonBackground: Color,
    val backBackground: Color,
    val cardBackground: Color,
    val icon: Color,
    val textOnBackground: Color,
    val textOnCard: Color,
    val textOnButton: Color,
    val activeIndicator: Color,
    val inactiveIndicator: Color,
)

@Immutable
data class CustomTypography(
    val title: TextStyle,
    val label: TextStyle,
    val button: TextStyle,
)

@Immutable
data class CustomShape(
    val unselectedCard: CornerBasedShape,
    val selectedCard: CornerBasedShape,
    val badge: CornerBasedShape,
    val button: CornerBasedShape,
)

@Immutable
data class CustomElevation(
    val default: Dp,
    val pressed: Dp
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        cardBackground = Color.Unspecified,
        backBackground = Color.Unspecified,
        buttonBackground = Color.Unspecified,
        textOnBackground = Color.Unspecified,
        textOnButton = Color.Unspecified,
        textOnCard = Color.Unspecified,
        icon = Color.Unspecified,
        background = Color.Unspecified,
        activeIndicator = Color.Unspecified,
        inactiveIndicator = Color.Unspecified,
        useDarkIcons = false,
    )
}

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        title = TextStyle.Default,
        button = TextStyle.Default,
        label = TextStyle.Default,
    )
}

val LocalCustomShapes = staticCompositionLocalOf {
    CustomShape(
        unselectedCard = RoundedCornerShape(10.dp),
        selectedCard = RoundedCornerShape(10.dp),
        badge = RoundedCornerShape(5.dp),
        button = RoundedCornerShape(4.dp),
    )
}

val LocalCustomElevation = staticCompositionLocalOf {
    CustomElevation(
        default = Dp.Unspecified,
        pressed = Dp.Unspecified
    )
}

@Composable
fun KickstarterTheme(
    selectedTheme: String,
    selectedThemeBrightness: String,
    content: @Composable () -> Unit
) {
    val systemIsDark = when (selectedThemeBrightness) {
        "light" -> false
        "dark" -> true
        "system" -> isSystemInDarkTheme()
        else -> throw IllegalStateException("$selectedThemeBrightness brighness does not exist")
    }
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    val isDark by isDarkState

    val customColors = when (selectedTheme) {
        "default" -> DefaultAppColors(systemIsDark)
        else -> throw IllegalStateException("$selectedTheme theme does not exist")
    }

    val PlayfairDisplay_Bold = FontFamily(Font(font.PlayfairDisplay_Bold))
    val RobotoCondensed_Medium = FontFamily(Font(font.RobotoCondensed_Medium))

    val customTypography = CustomTypography(
        title = TextStyle(
            fontFamily = PlayfairDisplay_Bold,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
            color = AppTheme.colors.textOnBackground,
        ),
        button = TextStyle(
            fontFamily = RobotoCondensed_Medium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.sp,
        ),
        label = TextStyle(
            fontFamily = RobotoCondensed_Medium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.sp,
        ),
    )

    val customElevation = CustomElevation(
        default = 4.dp,
        pressed = 8.dp
    )

    val customShape = CustomShape(
        selectedCard = RoundedCornerShape(25.dp),
        unselectedCard = RoundedCornerShape(25.dp),
        badge = RoundedCornerShape(5.dp),
        button = RoundedCornerShape(25.dp),
    )

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        LocalCustomElevation provides customElevation,
        LocalCustomShapes provides customShape,
        LocalThemeIsDark provides isDarkState,
    ) {
        SystemAppearance(!isDark)
        content()
    }
}

object AppTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current

    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current

    val shapes: CustomShape
        @Composable
        get() = LocalCustomShapes.current

    val elevation: CustomElevation
        @Composable
        get() = LocalCustomElevation.current
}

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal expect fun SystemAppearance(isDark: Boolean)