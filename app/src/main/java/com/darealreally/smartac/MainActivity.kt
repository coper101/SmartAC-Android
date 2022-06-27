package com.darealreally.smartac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.darealreally.smartac.ui.SmartACApp
import com.darealreally.smartac.ui.theme.SmartACTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // extend content to status bar and gesture navigation bar (fullscreen)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SmartACTheme {
                ProvideWindowInsets {
                    SmartACApp()
                }
            }
        }
    }
}