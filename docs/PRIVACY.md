# Privacy — Hunter’s Companion

The companion has no accounts, analytics, advertising, or telemetry. The developer does not receive your game files, folder names, artwork, or app activity.

Starting in 0.3.0, choosing Download verified installer makes an HTTPS request directly to GitHub and its asset servers. Those services receive normal download request information, including your IP address and the app’s User-Agent. The app sends no game files, artwork or saves. Opening upstream instructions launches your browser. The port installer is cached in private app storage and checked before sharing its read-only URI with Android’s installer. Android asks before allowing installation from this app and before installing the port. Uninstalling the companion removes its cached installer; it does not uninstall the separate port.

When you select a folder through Android’s file picker, the app remembers its URI and requests persistent read-only access. It checks the presence of expected game entries and reads optional sce_sys/pic1.png artwork into memory. It does not modify the selected folder, copy game code, or access saves. Choosing another folder releases the previous stored read grant where Android permits it. Uninstalling removes app settings and grants. Your game files are not deleted.

The separate game port and any map/online services have their own privacy practices. This app does not control them.

Maintained by emerytech. Effective October 31, 2026. Questions: https://github.com/emerytech/hunters-companion/issues . Please do not post game files, saves, or personal information in public issues.

This companion/setup app was entirely LLM-coded for the maintainer’s personal Thor setup and shared to save others repeating that work and token expenditure. The upstream port is independently developed and credited in the README.
