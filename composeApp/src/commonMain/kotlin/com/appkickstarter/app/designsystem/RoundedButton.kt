package com.appkickstarter.app.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appkickstarter.app.theme.KickstarterTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
 
@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = KickstarterTheme.colors.buttonBackground,
    contentColor: Color = KickstarterTheme.colors.textOnButton,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
        ),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(text, style = KickstarterTheme.typography.button)
        }
    }
}

@Preview
@Composable
fun RoundedButton_Preview() {
    KickstarterTheme(
        "default",
        "light"
    ) {
        RoundedButton("Rounded Button") {
            
        }
    }
}