package dev.lucianosantos.flavorflowxmlsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.lucianosantos.flavorflowxmlsample.databinding.ActivityMainBinding

/**
 * Single-screen sample. Everything that varies per white-label client comes from
 * resources that apply-flavor-action rewrites at build time:
 *  - app name  -> R.string.app_name (strings.xml)
 *  - colours   -> @color/primary_color, @color/secondary_color, ... (colors.xml)
 *  - theme     -> Theme.<AppName> (themes.xml + manifest)
 * Nothing here is hardcoded per flavor.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.packageName.text = packageName
    }
}
