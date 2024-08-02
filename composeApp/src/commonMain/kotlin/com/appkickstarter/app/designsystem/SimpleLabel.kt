package com.appkickstarter.app.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.appkickstarter.app.theme.KickstarterTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
 
@Composable
 fun SimpleLabel(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    color: Color = KickstarterTheme.colors.textOnBackground,
    bold: Boolean = false
) {
    Text(
        text,
        modifier = modifier,
        color = color,
        style = KickstarterTheme.typography.label.copy(
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        ),
        textAlign = textAlign
    )
}

@Preview
@Composable
fun SimpleLabel_Preview() {
    KickstarterTheme(
        "default",
        "light"
    ) {
        SimpleLabel("Simple Label") 
    }
}
