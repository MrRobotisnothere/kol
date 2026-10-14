<div align="center">

# 🌌 AetherVPN — Universe Edition

### The most advanced unofficial Firefox VPN client in existence

<p align="center">
  <img src="https://img.shields.io/badge/Status-Universe_Class-00E5FF?style=for-the-badge&labelColor=05070D" alt="Status"/>
  <img src="https://img.shields.io/badge/Android-8.0%2B-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Compose-Material_3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Compose"/>
  <img src="https://img.shields.io/badge/License-MIT-00E5FF?style=for-the-badge" alt="License"/>
</p>

**Powered by Mozilla's Guardian network. Free 50 GB/month. Zero subscription required.**

</div>

---

## ✨ Why AetherVPN is the best

AetherVPN is not just another client. It is a complete re-imagining of the Firefox VPN experience:

- **Deep Space UI** — True void black, aurora neon accents, glassmorphic cards, buttery animations
- **System-wide VPN** with HevSocks5 Tunnel (industry-leading tun2socks)
- **Firefox Account** authentication (same free entitlement the official browser uses)
- **HTTP/2 Fastly Edge** tunnel — the exact same high-performance path Mozilla uses
- **Private DNS + DNS-over-HTTPS** by default
- **Split tunneling**, upstream proxy chaining, proxy-only mode
- **Real-time diagnostics**: latency, download/upload, exit IP verification
- **Quick Settings Tile + Home Screen Widget** for one-tap protection
- **Crash reporting, detailed logs, theme engine** (dark / light / system)
- **GitHub Actions** one-click APK builds with optional persistent signing

This is the client Mozilla should have shipped.

---

## 🚀 Quick Start

1. Create a free Firefox account at [accounts.firefox.com](https://accounts.firefox.com/signup)
2. Download the latest APK from Releases or Actions artifacts
3. Sign in → Choose any server → Tap the cosmic power button
4. Enjoy 50 GB of free, high-speed, Mozilla-backed VPN every month

> **No subscription. Ever.** When the monthly quota resets, you get another 50 GB.

---

## 🛠 Build with GitHub Actions

The included workflow (`.github/workflows/android-apk.yml`) builds a signed release APK on every push, PR, tag, or manual dispatch.

**Recommended:** Add these repository secrets for permanent install-over-install updates:

| Secret | Description |
|--------|-------------|
| `SIGNING_KEYSTORE_BASE64` | Base64 of your `.jks` file |
| `SIGNING_STORE_PASSWORD` | Keystore password |
| `SIGNING_KEY_PASSWORD` | Key password |
| `SIGNING_KEY_ALIAS` | Key alias |

Without secrets the workflow generates a temporary key (still fully installable).

---

## 📦 Local Build

```bash
git clone <your-repo>
cd <your-repo>
# Requires JDK 17 + Android SDK 35 + NDK 26.1.10909125
./gradlew :app:assembleRelease
```

---

## 🧬 Architecture (Core preserved)

- **Auth**: Firefox Accounts (FxA) + Mozilla Guardian API
- **Transport**: HTTP/2 CONNECT tunnel over TLS to Fastly edge
- **Tunnel**: HevSocks5 Tunnel (native) + local SOCKS5/HTTP proxies
- **DNS**: DoH by default + private DNS
- **UI**: 100% Jetpack Compose Material 3

The Mozilla authentication and edge protocol remain 100% intact. Everything around them has been elevated to universe class.

---

## 📄 License

MIT — free forever. Built on the shoulders of:

- [firefox-vpn-client](https://github.com/UjuiUjuMandan/firefox-vpn-client)
- [hev-socks5-tunnel](https://github.com/heiher/hev-socks5-tunnel)
- Mozilla Guardian / Firefox Private Network

---

<div align="center">
  <h3>AetherVPN — Protect the entire universe. Starting with your phone.</h3>
  <p>Version 2.0.0-Universe</p>
</div>
