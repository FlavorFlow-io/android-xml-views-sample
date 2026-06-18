# FlavorFlow — Android XML Views white-label sample

A complete, working example **and step-by-step guide** for shipping a single
Android app built with **traditional XML Views** that is automatically re-branded
for each of your clients with [FlavorFlow](https://flavorflow.io). It is the Views
counterpart of the
[android-jetpack-compose-sample](https://github.com/FlavorFlow-io/android-jetpack-compose-sample).

## What is a white-label build?

With FlavorFlow you define your **clients** once — each with its own app name,
package / application id, colors, logo, and config. A GitHub Actions workflow
then fetches those clients and produces **one branded APK per client** from this
single codebase. No per-client branches, forks, or copy-paste.

The pipeline is two FlavorFlow actions wrapped around your normal Gradle build:

1. **[`fetch-flavors-action`](https://github.com/FlavorFlow-io/fetch-flavors-action)** —
   pulls every client from your project and emits them as a build matrix.
2. **[`apply-flavor-action`](https://github.com/FlavorFlow-io/apply-flavor-action)** —
   for each client, rewrites the app's branding (XML theme, colors, app name,
   package / application id, launcher icon) before the build.
3. **`./gradlew`** — builds the APK for that client.

The whole thing lives in [`.github/workflows/build-white-label.yml`](.github/workflows/build-white-label.yml).

## Build your own — step by step

1. **Create a project on [flavorflow.io](https://flavorflow.io)** and add a
   client for each brand (set its name, package name, and colors, and upload a
   logo).
2. **Grab your project API key and project ID** from the project settings.
3. **Make branding come from resources, not from code.** Reference the fixed
   resource names `apply-flavor-action` rewrites — `@color/primary_color`,
   `@color/on_primary`, …, `@string/app_name` — from your layouts and theme, and
   keep `android:theme` on `<application>` only (see the tip below).
4. **Copy [`.github/workflows/build-white-label.yml`](.github/workflows/build-white-label.yml)**
   into your repo.
5. **Add your credentials** under *Settings → Secrets and variables → Actions*:
   - Secret **`TEST_API_KEY`** → your project API key.
   - Variable **`TEST_PROJECT_ID`** → your project ID.

   (Rename them if you like — just keep the workflow in sync.)
6. **Push to `main`.** Actions builds one APK per client; download them from the
   run's **Artifacts**.

## What gets branded (and where)

`apply-flavor-action` (with `project-type: android-native-xml`) rewrites, per
client:

| What | Where |
| ---- | ----- |
| Colors | `res/values/colors.xml` → `primary_color`, `secondary_color`, `on_primary`, … |
| Theme | `res/values/themes.xml` (`Theme.<AppName>`) + `AndroidManifest.xml` |
| App name | `res/values/strings.xml` → `app_name` |
| Package / application id | `app/build.gradle.kts` |
| Launcher icon | generated from the client logo |

> **Tip:** put `android:theme` on `<application>` only, not on `<activity>`. The
> action regenerates `themes.xml` as `Theme.<AppName>` and updates only the
> application-level theme; an activity-level theme would keep pointing at the old
> style name and fail resource linking. Let activities inherit the app theme.

## Theming approaches

This sample uses the **CI-plugin** approach (the action rewrites your resources
automatically). FlavorFlow supports two more — generating resources at **build
time** with Gradle `resValue`, or fetching the theme at **run time** from the API
and applying it to your views. Full guide:

**https://flavorflow.io/docs/platforms/android-views**

## Run it locally

```bash
./gradlew assembleDebug      # build the base (un-branded) app
```

---

### Note: the screenshot test

This repo also contains a [Roborazzi](https://github.com/takahirom/roborazzi)
screenshot test (`ThemeScreenshotTest`) with per-client golden images. **You don't
need any of this for your own white-label app** — it's how *we* make sure
`apply-flavor-action` keeps theming correctly as FlavorFlow evolves. Feel free to
ignore or delete it.
