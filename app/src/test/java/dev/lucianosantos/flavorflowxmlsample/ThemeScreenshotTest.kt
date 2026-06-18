package dev.lucianosantos.flavorflowxmlsample

import androidx.test.core.app.ActivityScenario
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Screenshot test that validates theme application for the XML Views sample.
 *
 * Nothing here is flavor-specific: it launches the app's own MainActivity, which
 * inflates activity_main.xml using the app theme, colours (@color/primary_color, ...)
 * and name (R.string.app_name). All of those are rewritten per client at build time
 * by apply-flavor-action (project-type: android-native-xml), so the rendered colours
 * and name are whatever the applied flavor sets — proving theme application works
 * without hardcoding anything.
 *
 * Run via the Roborazzi Gradle tasks:
 *   ./gradlew recordRoborazziDebug   # write app/src/test/snapshots/theme.png
 *   ./gradlew verifyRoborazziDebug   # compare against the committed golden
 *
 * In the white-label matrix, scripts/select-screenshot-golden.sh copies the golden
 * for the flavor being built into that reference path before verify runs.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w360dp-h640dp-xhdpi")
class ThemeScreenshotTest {

    @Test
    fun themeShowcase() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.window.decorView.captureRoboImage("src/test/snapshots/theme.png")
            }
        }
    }
}
