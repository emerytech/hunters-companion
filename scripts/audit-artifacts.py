#!/usr/bin/env python3
"""Reject unintended game assets/code and private keys in candidate outputs."""
import sys, zipfile
for filename in sys.argv[1:]:
    with zipfile.ZipFile(filename) as archive:
        for name in archive.namelist():
            lower = name.lower()
            if lower.endswith(('.apk', '.png', '.jpg', '.jpeg', '.dds', '.so', '.bin', '.pkg', '.keystore', '.jks')):
                raise SystemExit(f'Unexpected payload in {filename}: {name}')
    print(f'Payload audit passed: {filename}')
