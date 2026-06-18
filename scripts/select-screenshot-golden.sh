#!/usr/bin/env bash
#
# Selects the committed golden image for a flavor (by its app name) and copies it
# into the canonical Roborazzi reference path that `verifyRoborazziDebug` checks.
#
# There is ONE screenshot test (ThemeScreenshotTest.themeShowcase) and therefore
# ONE reference path (app/src/test/snapshots/theme.png, git-ignored). We keep a
# per-flavor golden under app/screenshots/goldens/<app name>.png and swap the
# matching one into the reference path right before verify, so every flavor in the
# white-label matrix is validated against ITS OWN golden.
#
# Usage: scripts/select-screenshot-golden.sh "<app name>"
set -euo pipefail

APP_NAME="${1:-}"
if [ -z "$APP_NAME" ]; then
  echo "::error::Usage: select-screenshot-golden.sh <app name>" >&2
  exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

GOLDENS_DIR="$REPO_ROOT/app/screenshots/goldens"
GOLDEN="$GOLDENS_DIR/${APP_NAME}.png"
REF_FILE="$REPO_ROOT/app/src/test/snapshots/theme.png"

if [ ! -f "$GOLDEN" ]; then
  echo "::error::No committed golden image for app name '$APP_NAME' (expected $GOLDEN)." >&2
  echo "Committed goldens are:" >&2
  ls -1 "$GOLDENS_DIR" >&2 || true
  exit 1
fi

mkdir -p "$(dirname "$REF_FILE")"
cp "$GOLDEN" "$REF_FILE"
echo "Selected golden for '$APP_NAME' -> $REF_FILE"
