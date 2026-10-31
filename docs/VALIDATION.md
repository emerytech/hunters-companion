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

## 0.3.0 combined setup — October 31, 2026

- Artifact fixture tests reject truncated/oversized/altered files, wrong checksums, HTTP, deceptive hosts, user-info URLs and unexpected ports; valid fixture/allowed hosts pass.
- Signed APK build and Android lint passed. Companion APK audit finds no bundled game assets, native libraries, game binaries, signing keys or nested runtime APK.
- On Thor: downloaded upstream 2.2.0 directly through the app; size, SHA-256, package/version and signer checks passed. Cached re-verification passed. Existing 2.2.0 was recognized and not replaced. Launch both retained the game on display 0 and companion on display 4.
- Clean Android 15 emulator: first-run setup reports runtime missing; download/verification proceeds to the install confirmation and Android unknown-source settings. Completing the OS installer UI was not verified because the test mirror failed to deliver setting changes reliably; test device was shut down. This does not count as a successful clean runtime installation test.
- Updated companion installed over the prior version on Thor, preserving chosen folder. No game files or game settings changed, and no game inputs were sent during these checks.
- LLM authorship disclosure added to README, privacy documentation, in-app Help and release notes, with separate upstream credit.

Remaining beta gaps include full fresh-install OS-confirmation testing, download interruption/cancellation on hardware, remapped controllers, removable/revoked folder access, non-Thor layouts and Android 16 behavior. No claim of a fused single APK, automatic native-code preparation, automatic game-exit detection, or added game-performance optimization.
