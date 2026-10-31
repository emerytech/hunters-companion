# Validation — 0.2.0 local candidate, 2026-10-31

Passed locally:
- Gradle assembleRelease, bundleRelease, lintRelease (zero errors; warnings remain for available tool updates, fixed orientation and English-only text).
- APK signature verification; APK/AAB payload audit: no PNG/JPEG/DDS, game binaries, native libraries, PKGs or signing keys. Original vector icon only.
- Installed update on AYN Thor Android 13. Fresh setup state uses neutral background and original icon.
- Android folder picker launches on lower display. Selecting Games parent reports required entries missing.
- Selecting Games/Bloodborne accepts required directories and nonempty eboot.bin, then loads user-supplied pic1.png.
- Force-stop/reopen companion retains persisted folder access and restores artwork.
- Cancelling subsequent folder selection preserves prior valid selection/artwork.
- Prior 0.1 preview also verified lower-screen artwork/controls, launcher return and upper-screen game task retention. Full regression of those actions on 0.2 remains pending.

Not claimed: CI run on GitHub, Google Play approval, Android 16 hardware/emulator testing, full gameplay, revoked/removable-storage testing, corrupted-image tests, clean port-absent test, universal launcher support, automatic launch/exit integration, or a Google Play release.

Only the companion was updated/restarted; no game save loaded or changed. Device screenshots with game artwork are private local evidence, not included in source or public listing assets.
