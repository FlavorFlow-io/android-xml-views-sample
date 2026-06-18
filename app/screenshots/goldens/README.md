# Per-flavor screenshot goldens

Reference images for the theme screenshot test (`ThemeScreenshotTest.themeShowcase`
in `app/src/test/...`). There is **one golden per flavor**, captured from a render of
the app *after* `apply-flavor-action` has rewritten the XML theme/colours/name for
that flavor.

## Naming convention

```
<app name>.png
```

The file name is the flavor's **exact `app_name`** (the same value that appears in
the white-label build matrix and in the per-flavor job name), e.g.
`Cara de Pastel.png`, `Pão Duro.png`, `To Com Fome.png`.

## How they are used

Roborazzi verifies the test against a single canonical reference path
(`app/src/test/snapshots/theme.png`, git-ignored). Before verify runs, the
white-label matrix calls `scripts/select-screenshot-golden.sh "<app name>"`, which
copies the golden for the flavor currently being built into that path.

## Bootstrapping / regenerating

Until a golden exists for a flavor, the white-label workflow runs
`recordRoborazziDebug` instead of verify and uploads the rendered image as the
`screenshot-<flavor>` artifact. To turn a recorded image into a committed golden:

1. Download the `screenshot-<flavor>` artifact from the white-label run.
2. Save its `theme.png` here as `<app name>.png`.
3. Commit. Subsequent runs will `verifyRoborazziDebug` against it.
