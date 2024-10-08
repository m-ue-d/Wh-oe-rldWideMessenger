package spg.client.view.template

import javafx.beans.binding.Bindings
import javafx.beans.property.ObjectProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.util.StringConverter
import spg.client.model.Settings
import spg.client.view.utility.ColorUtil
import spg.client.view.utility.FontManager
import java.util.Collections.max

class ColorField(property: ObjectProperty<Color>) : TextField() {
	init {
		this.isFocusTraversable = false
		this.alignment = Pos.CENTER_LEFT
		this.font = FontManager.boldFont(16.0)
		this.styleProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding "-fx-text-fill: ${
					ColorUtil.toHex(
						Settings.colors["Primary Color"]!!.color.value.let {
							if (max(listOf(it.red, it.green, it.blue)) < 0.5) {
								Color.web("#ebf2ff")
							} else {
								Color.web("#1d1d21")
							}
						}
					)
				};"
			}, Settings.colors["Primary Color"]!!.color)
		)
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding Background(
					BackgroundFill(
						Settings.colors["Primary Color"]!!.color.value,
						CornerRadii(5.0),
						Insets.EMPTY
					)
				)
			}, Settings.colors["Primary Color"]!!.color)
		)
		Bindings.bindBidirectional(
			this.textProperty(),
			property,
			object : StringConverter<Color>() {
				override fun toString(obj : Color) : String {
					return ColorUtil.toHex(obj)
				}

				override fun fromString(str : String) : Color {
					return ColorUtil.toColor(str)
				}
			}
		)
	}
}