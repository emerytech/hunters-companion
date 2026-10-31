# Upstream runtime integration

Hunter’s Companion 0.3.0 is a guided downloader/launcher, not a merged game APK. It downloads the unchanged Bloodborne Android 2.2.0 release on user request:

- Project/source/license: https://github.com/ducvd89/Bloodborne-Android (GPL-2.0; see upstream LICENSE and dependency notices).
- Release: https://github.com/ducvd89/Bloodborne-Android/releases/tag/android-2.2.0
- APK: Bloodborne-2.2.0.apk, 62,889,966 bytes.
- SHA-256: e97bed80872e2552ae175d9e1189def61c9a9f086bf64fd7d065602340a6ebc0
- Package: com.ducvd89.bloodborne, versionCode 40.
- Signer SHA-256: baec6fac22c73501e4d0c4cf965fb257eb6bcbfb3ae05ffa2f23918dc49d86b2

Checks were made against the upstream release metadata and APK on October 31, 2026. A release change requires an explicit pin update and retesting. No latest-release endpoint is trusted at runtime and no game-specific patch, mod, cheat, save or native-code library is downloaded by the companion.

The runtime is not included in this repository, its GitHub release APK, or CI artifacts. Its own notices and license apply independently. Game code generated from user files remains outside this project. Users choose their own files separately in each app; no folder writes or native compilation are performed here. Runtime performance settings remain the upstream port's defaults, not additional Thor optimizations by this project.

## Installer behavior

Only HTTPS requests to GitHub and its asset hosts are followed, with bounded redirects, timeouts, expected byte count, streaming size limit, SHA-256, Android package/version and signing-certificate checks. Verified APKs remain in private app storage. A non-exported URI-grant provider exposes only that one APK read-only, rechecking its digest before serving it to Android's installer. There is no arbitrary URL/file installation API.

Existing 2.2.0 or newer installs are not replaced or downgraded. Android performs installation and certificate/update compatibility checks for older or missing installations. No silent installs, permission bypasses or uninstall-on-conflict behavior. Installation permission can be revoked afterward in Android settings. A cancelled or failed download does not launch an installer.
