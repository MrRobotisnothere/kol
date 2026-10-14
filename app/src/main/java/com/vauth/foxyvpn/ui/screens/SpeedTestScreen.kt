package com.vauth.foxyvpn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.NetworkPing
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

private val speedClient = OkHttpClient.Builder()
    .connectTimeout(12, TimeUnit.SECONDS)
    .readTimeout(25, TimeUnit.SECONDS)
    .writeTimeout(25, TimeUnit.SECONDS)
    .callTimeout(45, TimeUnit.SECONDS)
    .build()

private data class SpeedResult(val latencyMs: Long, val downloadMbps: Double, val uploadMbps: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeedTestScreen(onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var running by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Ready to test this connection") }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<SpeedResult?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Network diagnostics", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
            ) {
                Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.size(84.dp).clip(CircleShape).background(Color(0xFF00BFA5).copy(alpha = 0.14f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (running) CircularProgressIndicator(modifier = Modifier.size(42.dp), strokeWidth = 3.dp)
                        else Icon(Icons.Filled.Speed, contentDescription = null, tint = Color(0xFF00BFA5), modifier = Modifier.size(42.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(if (running) "Testing your connection" else "Internet speed test", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(status, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(18.dp))
                    Button(
                        onClick = {
                            if (running) return@Button
                            running = true
                            error = null
                            result = null
                            scope.launch {
                                try {
                                    result = runSpeedTest { status = it }
                                    status = "Test complete · results reflect the current route"
                                } catch (e: Exception) {
                                    error = e.message ?: "The test could not complete. Check your connection and try again."
                                    status = "Test failed"
                                } finally { running = false }
                            }
                        },
                        enabled = !running,
                        modifier = Modifier.fillMaxWidth(),
                    ) { Text(if (running) "Testing…" else "Run speed test", fontWeight = FontWeight.SemiBold) }
                }
            }
            Spacer(Modifier.height(18.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard(Modifier.weight(1f), Icons.Filled.NetworkPing, "LATENCY", result?.let { "${it.latencyMs} ms" } ?: "—")
                MetricCard(Modifier.weight(1f), Icons.Filled.CloudDownload, "DOWNLOAD", result?.let { formatMbps(it.downloadMbps) } ?: "—")
                MetricCard(Modifier.weight(1f), Icons.Filled.CloudUpload, "UPLOAD", result?.let { formatMbps(it.uploadMbps) } ?: "—")
            }
            if (error != null) {
                Spacer(Modifier.height(12.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("About this test", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Uses Cloudflare's public speed-test endpoint to estimate latency and transfer throughput. Results vary with signal quality, server load, and whether the VPN is connected. A small amount of test data is transferred; this is not a guarantee of sustained speed.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
        Column(Modifier.padding(vertical = 16.dp, horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = Color(0xFF00BFA5), modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(10.dp))
            Text(value, fontSize = 17.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Spacer(Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        }
    }
}

private suspend fun runSpeedTest(onStatus: (String) -> Unit): SpeedResult = withContext(Dispatchers.IO) {
    onStatus("Measuring response time…")
    val pingStart = System.nanoTime()
    speedClient.newCall(Request.Builder().url("https://www.cloudflare.com/cdn-cgi/trace").get().build()).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Latency check returned HTTP ${response.code}")
        response.body?.byteStream()?.use { stream -> while (stream.read(ByteArray(1024)) != -1) { /* drain */ } }
    }
    val latency = ((System.nanoTime() - pingStart) / 1_000_000).coerceAtLeast(1)

    onStatus("Measuring download speed…")
    val downloadBytes = 5_000_000L
    val downStart = System.nanoTime()
    var received = 0L
    speedClient.newCall(Request.Builder().url("https://speed.cloudflare.com/__down?bytes=$downloadBytes").get().build()).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Download test returned HTTP ${response.code}")
        val stream = response.body?.byteStream() ?: throw IOException("Empty download response")
        stream.use { input ->
            val buffer = ByteArray(16 * 1024)
            while (true) { val n = input.read(buffer); if (n < 0) break; received += n }
        }
    }
    val downSeconds = ((System.nanoTime() - downStart) / 1_000_000_000.0).coerceAtLeast(0.001)
    if (received <= 0) throw IOException("No download data received")

    onStatus("Measuring upload speed…")
    val upload = ByteArray(1_000_000)
    val upStart = System.nanoTime()
    speedClient.newCall(
        Request.Builder().url("https://speed.cloudflare.com/__up")
            .post(upload.toRequestBody("application/octet-stream".toMediaType())).build(),
    ).execute().use { response -> if (!response.isSuccessful) throw IOException("Upload test returned HTTP ${response.code}") }
    val upSeconds = ((System.nanoTime() - upStart) / 1_000_000_000.0).coerceAtLeast(0.001)
    SpeedResult(latency, received * 8.0 / downSeconds / 1_000_000.0, upload.size * 8.0 / upSeconds / 1_000_000.0)
}

private fun formatMbps(value: Double): String = if (value >= 100) "${value.roundToInt()} Mbps" else String.format(java.util.Locale.US, "%.1f Mbps", value)
