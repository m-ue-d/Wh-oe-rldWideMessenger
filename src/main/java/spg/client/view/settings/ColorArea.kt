package spg.client.view.settings

import javafx.scene.layout.VBox
import spg.client.model.Settings
import spg.client.view.SettingsView
import spg.client.view.template.ColorField

object ColorArea : VBox() {
    init {
        this.spacing = 10.0
        this.children.addAll(
            SettingsView.SettingsItem(
                "Primary Color",
                "The primary color of the application. It is the darkest color of all.",
                ColorField(Settings.bgPrimary)
            ),
            SettingsView.SettingsItem(
                "Secondary Color",
                "A slightly lighter variant of the primary color. Used for elevated elements.",
                ColorField(Settings.bgSecondary)
            ),
            SettingsView.SettingsItem(
                "Tertiary Color",
                "The lightest color in the application. Used for backgrounds and light fonts.",
                ColorField(Settings.bgTertiary)
            ),
            SettingsView.SettingsItem(
                "Accent Color",
                "A colorful alternative to the other colors. Mainly used for tab indicators.",
                ColorField(Settings.colorAccent)
            ),
            SettingsView.SettingsItem(
                "Font Color",
                "The main color of the application's font. Usually set to a color close to white.",
                ColorField(Settings.fontMain)
            )
        )
    }
}