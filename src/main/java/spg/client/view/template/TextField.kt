package spg.client.view.template

import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import spg.client.model.Settings
import spg.client.view.utility.ColorUtil
import spg.client.view.utility.FontManager

open class TextField(prompt: String? = null) : javafx.scene.control.TextField() {
	init {
		this.isFocusTraversable = false
		this.prefHeight = 40.0
		this.padding = Insets(10.0)
		this.alignment = Pos.CENTER_LEFT
		this.font = FontManager.boldFont(16.0)
		this.promptText = prompt ?: ""
		this.styleProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding "-fx-text-fill: ${
					ColorUtil.toHex(
						Settings.fontMain.value
					)
				};"
			}, Settings.fontMain)
		)
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding Background(
					BackgroundFill(
						Settings.bgPrimary.value,
						CornerRadii(5.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgPrimary)
		)
	}
}