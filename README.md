# Hunter’s Companion for AYN Thor — unofficial

A small second-screen companion for people who already have their own Bloodborne game files and the separate Bloodborne Android port. This is **not Bloodborne, an emulator, a game downloader, or an official Sony/FromSoftware product**.

## Full disclosure

I had this companion and setup app written entirely with an LLM so I could get Bloodborne running on my AYN Thor quickly and easily. I’m sharing it because other people may want to do the same without repeating the work or spending their own tokens rebuilding the same setup.

This is a personal, experimental project—not a professionally audited product. The tests and their limits are documented; bugs are possible. The LLM-written code here is the companion/setup app. The separate Bloodborne Android port, its native-code tools and other upstream projects are the work of their respective contributors, not mine. Their licenses and credits still apply.

## What it does
- Opens on the Thor’s lower display, leaving the game on the upper display.
- Shows a controls reference and artwork read locally from your selected game folder.
- Guides installation of a pinned official Bloodborne Android port, then opens the game on the upper display; closes back to your existing lower-screen launcher.
- Includes a neutral original icon/background. No game artwork, game code, native recompilation output or saves are bundled. The port installer is downloaded separately at your request.

## Setup
1. Download and install the APK from [GitHub Releases](https://github.com/emerytech/hunters-companion/releases). This is an early beta; see the validation notes for tested behavior and limits.
2. Open **Setup → Choose step → Download verified installer**. It downloads upstream Bloodborne Android 2.2.0 directly from GitHub (about 63 MB), checks size/SHA-256/package/signature, and opens Android’s installation confirmation when the port is missing or older. You may need to allow installation from this app in Android settings, then return and choose the same step again; the verified installer is cached. Existing 2.2.0/newer installations are left alone. You can also install the port yourself from https://github.com/ducvd89/Bloodborne-Android . Native game code still must be prepared by the separate port using your own files.
3. Put your extracted game in a folder, for example:
   ```text
   Games/Bloodborne/
   ├── eboot.bin
   ├── dvdroot_ps4/
   ├── sce_module/
   └── sce_sys/
       └── pic1.png  (optional artwork)
   ```
4. Open Hunter’s Companion → **Setup → Choose step → Choose game folder** → select that folder. Android grants this app read access only to the selected tree. Cancel leaves your previous selection unchanged.
5. Select the same folder separately in Bloodborne. Android does not share folder permissions between apps. Complete the port’s own first-run setup.
6. In the companion choose **Artwork** or **Controls**, then **Launch both**. **Back to launcher** closes only the companion, revealing the previous app below; it does not quit or save the game.

Checks identify missing top-level entries, not ownership, version, full integrity or guaranteed compatibility. Storage removal/revoked access requires choosing the folder again. Face buttons are described by physical position because Thor labels differ from Xbox labels; remapped controls may differ.

## Boundaries
This version requires opening the companion manually. No launch/exit monitoring or automatic game-exit detection. No live stats, input injection, cheats, save access, analytics, ads or broad storage permission. Internet access is used only for the user-requested official installer download; opening upstream instructions uses your browser. Android’s install permission is requested explicitly; there is no silent installation. Touching the lower display may move controller focus; tap **Launch both** to return to the game. It keeps the lower screen awake while visible: close it when done.

The port separately offers Hunter’s Atlas on the second screen. Enable it in the game’s menu if desired; it can replace this panel and downloads its own map data. See the upstream documentation for online/community server behavior; this companion provides no online feature.

## Build
Install JDK 17+ and Android SDK 36, set `ANDROID_HOME`, then:
```sh
./scripts/test-runtime.sh
./gradlew assembleDebug bundleRelease lintRelease
```
Debug APK: `build/outputs/apk/debug/`. Unsigned release bundle: `build/outputs/bundle/release/`. CI builds artifacts for review, never publishes a release.

For release signing, supply `SIGNING_KEYSTORE`, `SIGNING_STORE_PASSWORD`, `SIGNING_KEY_ALIAS`, `SIGNING_KEY_PASSWORD` as environment variables. Keep the keystore outside the repo and back it up securely. Published APK updates must retain the release signing certificate. Do not distribute private prototype output from earlier versions.

## Publishing
Distribution is through GitHub APK releases. No Google Play submission is planned. See [release checklist](docs/RELEASE.md), [privacy](docs/PRIVACY.md), and [validation](docs/VALIDATION.md).

Companion source code is MIT licensed. Bloodborne and its assets remain the property of their respective owners. References: https://bloodborne.wikidot.com/controls and the upstream Android port above.
