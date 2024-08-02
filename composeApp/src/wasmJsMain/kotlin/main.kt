import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.appkickstarter.app.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("AppKickstarter") {
        App()
    }
}
