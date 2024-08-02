package com.appkickstarter.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appkickstarter.composeapp.generated.resources.IndieFlower_Regular
import appkickstarter.composeapp.generated.resources.Res.font
import org.jetbrains.compose.resources.Font

@Immutable
data class CustomColors(
    val useDarkIcons: Boolean,
    val background: Color,
    val backgroundList: Color,
    val sheetBackground: Color,
    val badgeBackground: Color,
    val bottomBarBackground: Color,
    val selectedIconBottomBar: Color,
    val unselectedIconBottomBar: Color,
    val buttonBackground: Color,
    val backBackground: Color,
    val backIcon: Color,
    val cardBackground: Color,
    val selectedCardBackground: Color,
    val icon: Color,
    val subtitle: Color,
    val textOnBackground: Color,
    val textOnBadge: Color,
    val textOnCard: Color,
    val textOnButton: Color,
    val customCardBorder: Color,
    val textFieldBackground: Color,
    val bottomBarIndicatorColor: Color,
)

@Immutable
data class CustomTypography(
    val title: TextStyle,
    val label: TextStyle,
    val subtitle: TextStyle,
    val button: TextStyle,
    val textOnCard: TextStyle,
    val tab: TextStyle,
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
        backIcon = Color.Unspecified,
        bottomBarBackground = Color.Unspecified,
        buttonBackground = Color.Unspecified,
        selectedIconBottomBar = Color.Unspecified,
        sheetBackground = Color.Unspecified,
        subtitle = Color.Unspecified,
        textOnBackground = Color.Unspecified,
        textOnButton = Color.Unspecified,
        textOnCard = Color.Unspecified,
        unselectedIconBottomBar = Color.Unspecified,
        icon = Color.Unspecified,
        background = Color.Unspecified,
        backgroundList = Color.Unspecified,
        selectedCardBackground = Color.Unspecified,
        customCardBorder = Color.Unspecified,
        badgeBackground = Color.Unspecified,
        useDarkIcons = false,
        textFieldBackground = Color.Unspecified,
        textOnBadge = Color.Unspecified,
        bottomBarIndicatorColor = Color.Unspecified,
    )
}

 val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        title = TextStyle.Default,
        button = TextStyle.Default,
        textOnCard = TextStyle.Default,
        tab = TextStyle.Default,
        label = TextStyle.Default,
        subtitle = TextStyle.Default,
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
    platformHapticsEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val useDarkTheme = when (selectedThemeBrightness) {
        "light" -> false
        "dark" -> true
        "system" -> isSystemInDarkTheme()
        else -> throw IllegalStateException("$selectedThemeBrightness brighness does not exist")
    }
     val customColors = when (selectedTheme) {
        "default" -> DefaultAppColors(useDarkTheme)
        else -> throw IllegalStateException("$selectedTheme theme does not exist")
    } 

    val fontFactor = 1
/*
    val PlayfairDisplayRegular: FontFamily =
        fontFamilyResource(SharedRes.fonts.PlayfairDisplay.regular)
    val PlayfairDisplayBold: FontFamily = fontFamilyResource(
        SharedRes.fonts.PlayfairDisplay.bold,
    )
    val LatoBold: FontFamily = fontFamilyResource(SharedRes.fonts.Lato.bold)
    val LatoRegular: FontFamily = fontFamilyResource(SharedRes.fonts.Lato.regular)

    val InterBold: FontFamily = fontFamilyResource(SharedRes.fonts.Inter.bold)
    val InterLight: FontFamily = fontFamilyResource(SharedRes.fonts.Inter.light)
    val InterRegular: FontFamily = fontFamilyResource(SharedRes.fonts.Inter.regular)

    val BangerRegular: FontFamily = fontFamilyResource(SharedRes.fonts.Bangers.regular)
    val NunitoSemiBold: FontFamily = fontFamilyResource(SharedRes.fonts.Nunito.semiBold)
    val NunitoExtraBold: FontFamily = fontFamilyResource(SharedRes.fonts.Nunito.extraBold)

    val RobotoCondensedMedium: FontFamily = fontFamilyResource(SharedRes.fonts.RobotoCondensed.medium)
    val RobotoCondensedLight: FontFamily = fontFamilyResource(SharedRes.fonts.RobotoCondensed.light)
*/
    val indieFlower = FontFamily(Font(font.IndieFlower_Regular))
    
    val customTypography = CustomTypography(
        title = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp * fontFactor,
            lineHeight = 32.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
            color = KickstarterTheme.colors.textOnBackground,
        ),
        button = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp * fontFactor,
            lineHeight = 21.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
        ),
        textOnCard = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 19.sp * fontFactor,
            lineHeight = 28.5.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
        ),
        tab = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp * fontFactor,
            lineHeight = 20.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
        ),
        label = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp * fontFactor,
            lineHeight = 21.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
        ),
        subtitle = TextStyle(
            fontFamily = indieFlower,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp * fontFactor,
            lineHeight = 18.sp * fontFactor,
            letterSpacing = 0.sp * fontFactor,
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
        /*LocalPlatformConfiguration provides remember(
            useDarkTheme,
            platformHapticsEnabled
        ) {
            PlatformConfiguration(
                platformHaptics = platformHapticsEnabled,
                darkMode = useDarkTheme,
            )
        },*/
        //LocalHapticFeedback provides rememberHapticFeedback(),
        content = content
    )
}

object KickstarterTheme {
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
