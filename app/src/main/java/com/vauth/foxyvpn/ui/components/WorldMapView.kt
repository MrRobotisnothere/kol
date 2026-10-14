package com.vauth.foxyvpn.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.vauth.foxyvpn.ui.theme.NeonCyan
import com.vauth.foxyvpn.ui.theme.NeonPurple
import com.vauth.foxyvpn.ui.theme.CardSurface

/**
 * Lightweight stylized world mini-map.
 * Places a glowing pin at approximate lat/lon for the selected / connected country.
 */
@Composable
fun WorldMapView(
    countryCode: String?,
    isConnected: Boolean,
    modifier: Modifier = Modifier,
) {
    val infinite = rememberInfiniteTransition(label = "map-pulse")
    val pulse by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    val pinColor = if (isConnected) NeonCyan else NeonPurple
    val coords = countryToMapOffset(countryCode)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0E1320), Color(0xFF151C2C), Color(0xFF0E1320)),
                ),
            ),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // subtle grid
            val gridColor = Color(0xFF1E2738)
            for (i in 1..7) {
                val x = w * i / 8f
                drawLine(gridColor, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
            }
            for (i in 1..4) {
                val y = h * i / 5f
                drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
            }

            // Stylized polygonal continent silhouettes, projected into the panel.
            val land = Color(0xFF24354B)
            val coast = Color(0xFF3B5874)
            fun continent(points: List<Pair<Float, Float>>) {
                val path = Path().apply {
                    points.forEachIndexed { index, point ->
                        val x = w * point.first
                        val y = h * point.second
                        if (index == 0) moveTo(x, y) else lineTo(x, y)
                    }
                    close()
                }
                drawPath(path, land)
                drawPath(path, coast, style = Stroke(width = 1.2f))
            }
            continent(listOf(0.08f to 0.20f, 0.15f to 0.12f, 0.25f to 0.16f, 0.31f to 0.28f, 0.28f to 0.38f, 0.23f to 0.44f, 0.22f to 0.54f, 0.18f to 0.49f, 0.15f to 0.37f, 0.10f to 0.31f))
            continent(listOf(0.24f to 0.53f, 0.31f to 0.55f, 0.34f to 0.67f, 0.31f to 0.82f, 0.27f to 0.91f, 0.24f to 0.76f, 0.22f to 0.63f))
            continent(listOf(0.43f to 0.22f, 0.49f to 0.18f, 0.54f to 0.23f, 0.55f to 0.32f, 0.51f to 0.38f, 0.48f to 0.33f, 0.44f to 0.31f))
            continent(listOf(0.47f to 0.40f, 0.54f to 0.39f, 0.58f to 0.49f, 0.55f to 0.66f, 0.51f to 0.76f, 0.47f to 0.61f, 0.45f to 0.48f))
            continent(listOf(0.55f to 0.20f, 0.64f to 0.13f, 0.76f to 0.17f, 0.88f to 0.25f, 0.91f to 0.36f, 0.84f to 0.43f, 0.78f to 0.39f, 0.71f to 0.44f, 0.65f to 0.35f, 0.58f to 0.33f))
            continent(listOf(0.77f to 0.66f, 0.84f to 0.64f, 0.89f to 0.71f, 0.86f to 0.78f, 0.79f to 0.76f))

            // Dotted telemetry grid and a faint orbital sweep add depth without a map SDK.
            for (i in 0..22) {
                val x = w * (i + 0.5f) / 23f
                for (j in 0..9) {
                    val y = h * (j + 0.5f) / 10f
                    drawCircle(Color(0xFF526780).copy(alpha = 0.18f), radius = 1.1f, center = Offset(x, y))
                }
            }
            val orbit = Path().apply {
                moveTo(w * 0.08f, h * 0.67f)
                quadraticTo(w * 0.48f, -h * 0.10f, w * 0.92f, h * 0.58f)
            }
            drawPath(orbit, Color(0xFF3A5A77).copy(alpha = 0.45f), style = Stroke(width = 1.4f))

            // Equator
            drawLine(
                color = Color(0xFF2A3548),
                start = Offset(0f, h * 0.52f),
                end = Offset(w, h * 0.52f),
                strokeWidth = 1f,
            )

            if (coords != null) {
                val px = w * coords.first
                val py = h * coords.second

                // outer pulse ring
                drawCircle(
                    color = pinColor.copy(alpha = 0.15f + 0.25f * pulse),
                    radius = 28f + 18f * pulse,
                    center = Offset(px, py),
                )
                drawCircle(
                    color = pinColor.copy(alpha = 0.35f),
                    radius = 14f,
                    center = Offset(px, py),
                )
                // core
                drawCircle(
                    color = pinColor,
                    radius = 6.5f,
                    center = Offset(px, py),
                )
                // pin stem
                drawLine(
                    color = pinColor,
                    start = Offset(px, py + 6f),
                    end = Offset(px, py + 22f),
                    strokeWidth = 2.5f,
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}

/** Approximate normalized (x, y) map positions for common ISO country codes. */
private fun countryToMapOffset(code: String?): Pair<Float, Float>? {
    if (code.isNullOrBlank() || code.equals("REC", ignoreCase = true)) return null
    return when (code.uppercase()) {
        "US" -> 0.22f to 0.38f
        "CA" -> 0.20f to 0.28f
        "MX" -> 0.20f to 0.48f
        "BR" -> 0.30f to 0.68f
        "AR" -> 0.30f to 0.80f
        "GB", "UK" -> 0.47f to 0.28f
        "IE" -> 0.45f to 0.27f
        "FR" -> 0.48f to 0.32f
        "DE" -> 0.51f to 0.30f
        "NL" -> 0.50f to 0.28f
        "BE" -> 0.49f to 0.29f
        "CH" -> 0.50f to 0.33f
        "IT" -> 0.52f to 0.36f
        "ES" -> 0.46f to 0.36f
        "PT" -> 0.44f to 0.36f
        "SE" -> 0.53f to 0.22f
        "NO" -> 0.52f to 0.20f
        "FI" -> 0.56f to 0.20f
        "PL" -> 0.54f to 0.30f
        "CZ" -> 0.53f to 0.31f
        "AT" -> 0.53f to 0.33f
        "RO" -> 0.56f to 0.34f
        "GR" -> 0.55f to 0.38f
        "TR" -> 0.58f to 0.36f
        "UA" -> 0.58f to 0.30f
        "RU" -> 0.68f to 0.22f
        "AE" -> 0.62f to 0.44f
        "SA" -> 0.60f to 0.46f
        "IL" -> 0.58f to 0.40f
        "EG" -> 0.56f to 0.44f
        "ZA" -> 0.54f to 0.78f
        "NG" -> 0.50f to 0.52f
        "IN" -> 0.68f to 0.46f
        "SG" -> 0.76f to 0.56f
        "MY" -> 0.76f to 0.54f
        "ID" -> 0.80f to 0.60f
        "TH" -> 0.74f to 0.50f
        "VN" -> 0.76f to 0.48f
        "PH" -> 0.82f to 0.50f
        "JP" -> 0.86f to 0.36f
        "KR" -> 0.84f to 0.36f
        "CN" -> 0.76f to 0.36f
        "HK" -> 0.78f to 0.42f
        "TW" -> 0.82f to 0.42f
        "AU" -> 0.84f to 0.72f
        "NZ" -> 0.92f to 0.82f
        else -> 0.50f to 0.45f
    }
}
