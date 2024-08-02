package com.appkickstarter.app.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.appkickstarter.app.theme.KickstarterTheme
import com.appkickstarter.app.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
 
@Composable
fun Heading(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    style: TextStyle = AppTheme.typography.title,
    color: Color = AppTheme.colors.textOnBackground
) {
    Text(
        text,
        modifier = modifier,
        color = color,
        style = style,
        textAlign = textAlign
    )
}

@Preview
@Composable
fun Heading_Preview() {
    KickstarterTheme(
        "default",
        "light"
    ) {
        Heading("Heading")
    }
}