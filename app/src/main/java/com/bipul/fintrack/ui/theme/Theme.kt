package com.bipul.fintrack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// =====================================
// DARK THEME
// =====================================

private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFF8FBC98),
    onPrimary = Color(0xFF17331D),

    primaryContainer = Color(0xFF315438),
    onPrimaryContainer = Color(0xFFD0E8D2),

    secondary = Color(0xFFAFC8B2),
    onSecondary = Color(0xFF1B321F),

    secondaryContainer = Color(0xFF3A513E),
    onSecondaryContainer = Color(0xFFD5EBD7),

    tertiary = Color(0xFFD5B88A),
    onTertiary = Color(0xFF392B17),

    background = Color(0xFF0F1410),
    onBackground = Color(0xFFE7EDE7),

    surface = Color(0xFF171C18),
    onSurface = Color(0xFFE7EDE7),

    surfaceVariant = Color(0xFF414941),
    onSurfaceVariant = Color(0xFFC1CAC1),

    outline = Color(0xFF8B948B)
)

// =====================================
// LIGHT THEME
// =====================================

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF4F7C59),
    onPrimary = Color.White,

    primaryContainer = Color(0xFFD1E8D4),
    onPrimaryContainer = Color(0xFF0C2813),

    secondary = Color(0xFF526B56),
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFD5E8D7),
    onSecondaryContainer = Color(0xFF102B16),

    tertiary = Color(0xFF765B32),
    onTertiary = Color.White,

    background = Color(0xFFF8FAF8),
    onBackground = Color(0xFF191D19),

    surface = Color.White,
    onSurface = Color(0xFF191D19),

    surfaceVariant = Color(0xFFE0E8E0),
    onSurfaceVariant = Color(0xFF424942),

    outline = Color(0xFF727A72)
)

// =====================================
// FINTRACK THEME
// =====================================

@Composable
fun FinTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {

        dynamicColor &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme

        else -> LightColorScheme
    }

    val view = LocalView.current

    SideEffect {

        val window =
            (view.context as Activity).window

        val backgroundColor =
            colorScheme.background.toArgb()

        // Activity uncovered area
        window.decorView.setBackgroundColor(
            backgroundColor
        )

        // Status bar
        window.statusBarColor =
            backgroundColor

        // Navigation bar
        window.navigationBarColor =
            backgroundColor

        val controller =
            WindowCompat.getInsetsController(
                window,
                view
            )

        // Light theme = dark system icons
        controller.isAppearanceLightStatusBars =
            !darkTheme

        controller.isAppearanceLightNavigationBars =
            !darkTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}