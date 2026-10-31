#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p build/runtime-tests
javac -d build/runtime-tests src/com/temery/hunterscompanion/RuntimeArtifact.java tests/RuntimeArtifactTest.java
java -cp build/runtime-tests com.temery.hunterscompanion.RuntimeArtifactTest
