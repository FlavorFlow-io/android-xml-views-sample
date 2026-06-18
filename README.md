# FlavorFlow — Android XML Views sample

A minimal Android app built with **traditional XML Views** (not Compose) that is
re-branded per white-label client by
[FlavorFlow](https://flavorflow.io) in CI. It is the Views counterpart of the
[android-jetpack-compose-sample](https://github.com/FlavorFlow-io/android-jetpack-compose-sample).

Everything client-specific lives in resources that
[`apply-flavor-action`](https://github.com/FlavorFlow-io/apply-flavor-action)
rewrites at build time (`project-type: android-native-xml`):

| What | Where |
| ---- | ----- |
| App name | `res/values/strings.xml` → `app_name` |
| Colours | `res/values/colors.xml` → `primary_color`, `secondary_color`, `on_primary`, … |
| Theme | `res/values/themes.xml` (`Theme.<AppName>`) + `AndroidManifest.xml` |
| Package / app id | `app/build.gradle.kts` |
| Launcher icon | generated from the client logo |

## Screenshot test (Roborazzi)

`app/src/test/.../ThemeScreenshotTest.kt` launches `MainActivity` under
Robolectric and renders it off-device (no emulator). It hardcodes nothing — the
colours and name come from the (flavor-rewritten) resources, so the render proves
theme application worked.

```bash
./gradlew recordRoborazziDebug   # write app/src/test/snapshots/theme.png
./gradlew verifyRoborazziDebug   # compare against the committed golden
```

Per-flavor goldens live in `app/screenshots/goldens/<app name>.png`; see that
folder's README for the record→commit→verify bootstrap.

## CI

- **`ci.yml`** — builds the app and records the base-theme screenshot. No secrets.
- **`build-white-label.yml`** — fetches every client from FlavorFlow and builds +
  screenshots one APK per client in a matrix. Requires repo secret
  `TEST_API_KEY` and repo variable `TEST_PROJECT_ID`.
