package com.vauth.foxyvpn.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// AetherVPN – Deep Space + Aurora palette (Universe Edition)
val NeonCyan = Color(0xFF00E5FF)
val NeonPurple = Color(0xFFB388FF)
val NeonMagenta = Color(0xFFFF4081)
val NeonBlue = Color(0xFF448AFF)
val DeepBg = Color(0xFF05070D)
val DeepSurface = Color(0xFF0C1018)
val CardSurface = Color(0xFF141A24)
val CardSurfaceElevated = Color(0xFF1C2433)
val SoftWhite = Color(0xFFF0F4FF)
val MutedGray = Color(0xFF8A94A8)

val md_theme_light_primary = Color(0xFF5B2EFF)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFE6DEFF)
val md_theme_light_onPrimaryContainer = Color(0xFF1A0066)
val md_theme_light_inversePrimary = Color(0xFFC9B8FF)

val md_theme_light_secondary = Color(0xFF00A896)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFB2F5EC)
val md_theme_light_onSecondaryContainer = Color(0xFF00201C)

val md_theme_light_tertiary = Color(0xFF7C4DFF)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFE8DDFF)
val md_theme_light_onTertiaryContainer = Color(0xFF25005A)

val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onErrorContainer = Color(0xFF410002)

val md_theme_light_background = Color(0xFFF7F5FF)
val md_theme_light_onBackground = Color(0xFF1A1B23)
val md_theme_light_surface = Color(0xFFF7F5FF)
val md_theme_light_onSurface = Color(0xFF1A1B23)
val md_theme_light_surfaceVariant = Color(0xFFE4DFF5)
val md_theme_light_onSurfaceVariant = Color(0xFF474554)
val md_theme_light_surfaceTint = md_theme_light_primary

val md_theme_light_surfaceContainerLowest = Color(0xFFFFFFFF)
val md_theme_light_surfaceContainerLow = Color(0xFFF1EEFF)
val md_theme_light_surfaceContainer = Color(0xFFEBE7FA)
val md_theme_light_surfaceContainerHigh = Color(0xFFE5E0F5)
val md_theme_light_surfaceContainerHighest = Color(0xFFDFDAF0)
val md_theme_light_surfaceBright = Color(0xFFF7F5FF)
val md_theme_light_surfaceDim = Color(0xFFD8D4E8)

val md_theme_light_outline = Color(0xFF787586)
val md_theme_light_outlineVariant = Color(0xFFC8C4D8)
val md_theme_light_inverseSurface = Color(0xFF2F3038)
val md_theme_light_inverseOnSurface = Color(0xFFF2F0F8)
val md_theme_light_scrim = Color(0xFF000000)

// Dark – primary experience
val md_theme_dark_primary = NeonCyan
val md_theme_dark_onPrimary = Color(0xFF003731)
val md_theme_dark_primaryContainer = Color(0xFF005048)
val md_theme_dark_onPrimaryContainer = Color(0xFF70F7E4)
val md_theme_dark_inversePrimary = Color(0xFF006B60)

val md_theme_dark_secondary = NeonPurple
val md_theme_dark_onSecondary = Color(0xFF2A0068)
val md_theme_dark_secondaryContainer = Color(0xFF4A1A9E)
val md_theme_dark_onSecondaryContainer = Color(0xFFE6DEFF)

val md_theme_dark_tertiary = NeonMagenta
val md_theme_dark_onTertiary = Color(0xFF4A0028)
val md_theme_dark_tertiaryContainer = Color(0xFF7A0045)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8E7)

val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

val md_theme_dark_background = DeepBg
val md_theme_dark_onBackground = SoftWhite
val md_theme_dark_surface = DeepSurface
val md_theme_dark_onSurface = SoftWhite
val md_theme_dark_surfaceVariant = CardSurface
val md_theme_dark_onSurfaceVariant = MutedGray
val md_theme_dark_surfaceTint = NeonCyan

val md_theme_dark_surfaceContainerLowest = Color(0xFF06080E)
val md_theme_dark_surfaceContainerLow = DeepSurface
val md_theme_dark_surfaceContainer = CardSurface
val md_theme_dark_surfaceContainerHigh = CardSurfaceElevated
val md_theme_dark_surfaceContainerHighest = Color(0xFF2A3140)
val md_theme_dark_surfaceBright = Color(0xFF2E3545)
val md_theme_dark_surfaceDim = DeepBg

val md_theme_dark_outline = Color(0xFF6B7388)
val md_theme_dark_outlineVariant = Color(0xFF3A4152)
val md_theme_dark_inverseSurface = SoftWhite
val md_theme_dark_inverseOnSurface = Color(0xFF1A1B23)
val md_theme_dark_scrim = Color(0xFF000000)

data class FoxyStatusColors(
    val connected: Color,
    val connecting: Color,
    val disconnected: Color,
)

val LightStatusColors = FoxyStatusColors(
    connected = Color(0xFF0D9F6E),
    connecting = Color(0xFFC47A00),
    disconnected = md_theme_light_onSurfaceVariant,
)

val DarkStatusColors = FoxyStatusColors(
    connected = NeonCyan,
    connecting = Color(0xFFFFC46B),
    disconnected = MutedGray,
)

val LocalFoxyStatusColors = staticCompositionLocalOf { DarkStatusColors }

val ConnectedGreen = DarkStatusColors.connected
val ConnectingAmber = DarkStatusColors.connecting
