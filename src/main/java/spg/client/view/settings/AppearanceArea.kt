package spg.client.view.settings

import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.VBox
import spg.client.model.Settings
import spg.client.view.SettingsView.SettingsItem
import spg.client.view.SettingsView.SettingsGroup
import spg.client.view.template.ColorField

object AppearanceArea : VBox() {
    init {
        this.spacing = 10.0
        this.children.add(SettingsGroup("Appearance"))
        this.children.addAll(
            Settings.colors.map {
                SettingsItem(
                    it.key,
                    it.value.description,
                    ColorField(it.value.color)
                )
            }
        )

        this.backgroundProperty().bind(
            Bindings.createObjectBinding({
                Background(
                    BackgroundFill(
                        Settings.colors["Secondary Color"]!!.color.value,
                        CornerRadii(10.0),
                        Insets.EMPTY
                    )
                )
            }, Settings.colors["Secondary Color"]!!.color)
        )
    }
}