# Hunter’s Companion for AYN Thor — unofficial

A small second-screen companion for people who already have their own Bloodborne game files and the separate Bloodborne Android port. This is **not Bloodborne, an emulator, a game downloader, or an official Sony/FromSoftware product**.

## What it does
- Opens on the Thor’s lower display, leaving the game on the upper display.
- Shows a controls reference and artwork read locally from your selected game folder.
- Opens the installed game on the upper display; closes back to your existing lower-screen launcher.
- Includes a neutral original icon/background. No game artwork, game code, native recompilation output, saves, installer or emulator is included.

## Setup
1. Download and install the APK from [GitHub Releases](https://github.com/emerytech/hunters-companion/releases). This is an early beta; see the validation notes for tested behavior and limits.
2. Install the separate port from its official project: https://github.com/ducvd89/Bloodborne-Android . Follow its requirements for your own extracted original 1.09 files and native-code preparation. This companion neither installs that port nor builds game code.
3. Put your extracted game in a folder, for example:
   ```text
   Games/Bloodborne/
   ├── eboot.bin
   ├── dvdroot_ps4/
   ├── sce_module/
   └── sce_sys/
       └── pic1.png  (optional artwork)
   ```
4. Open Hunter’s Companion → **Setup** → select that folder. Android grants this app read access only to the selected tree. Cancel leaves your previous selection unchanged.
5. Select the same folder separately in Bloodborne. Android does not share folder permissions between apps. Complete the port’s own first-run setup.
6. In the companion choose **Artwork** or **Controls**, then **Open game**. **Back to launcher** closes only the companion, revealing the previous app below; it does not quit or save the game.

Checks identify missing top-level entries, not ownership, version, full integrity or guaranteed compatibility. Storage removal/revoked access requires choosing the folder again. Face buttons are described by physical position because Thor labels differ from Xbox labels; remapped controls may differ.

## Boundaries
This version requires opening the companion manually. No launch/exit monitoring or automatic game-exit detection. No live stats, input injection, cheats, save access, analytics, ads, Internet permission or broad storage permission. Touching the lower display may move controller focus; tap **Open game** to return to the game. It keeps the lower screen awake while visible: close it when done.

The port separately offers Hunter’s Atlas on the second screen. Enable it in the game’s menu if desired; it can replace this panel and downloads its own map data. See the upstream documentation for online/community server behavior; this companion provides no online feature.

## Build
Install JDK 17+ and Android SDK 36, set `ANDROID_HOME`, then:
```sh
./gradlew assembleDebug bundleRelease lintRelease
```
Debug APK: `build/outputs/apk/debug/`. Unsigned release bundle: `build/outputs/bundle/release/`. CI builds artifacts for review, never publishes a release.

For release signing, supply `SIGNING_KEYSTORE`, `SIGNING_STORE_PASSWORD`, `SIGNING_KEY_ALIAS`, `SIGNING_KEY_PASSWORD` as environment variables. Keep the keystore outside the repo and back it up securely. Published APK updates must retain the release signing certificate. Do not distribute private prototype output from earlier versions.

## Publishing
Distribution is through GitHub APK releases. No Google Play submission is planned. See [release checklist](docs/RELEASE.md), [privacy](docs/PRIVACY.md), and [validation](docs/VALIDATION.md).

Companion source code is MIT licensed. Bloodborne and its assets remain the property of their respective owners. References: https://bloodborne.wikidot.com/controls and the upstream Android port above.
