# GitHub beta releases

Distribute only the companion APK, SHA256SUMS and source. Never distribute user game assets, generated native game code, saves, signing keys or private screenshots. Automated CI artifacts are test builds, not the signed release.

Before a release: run assembleRelease and lintRelease, audit the exact APK payload, verify its signature, and check setup on the Thor. Preserve the existing certificate for upgrades. The publisher keeps signing credentials outside the repository.

Label early versions as prereleases and state known gaps in the release notes. Source and builds contain only an original vector icon and neutral fallback background; game artwork is read from the user's chosen folder. Do not imply Sony, FromSoftware or AYN endorsement.

Google Play is out of scope. No store listing or submission is configured.
