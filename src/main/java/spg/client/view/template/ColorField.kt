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

class ColorField(property: ObjectProperty<Color>) : TextField() {
	init {
		this.isFocusTraversable = false
		this.alignment = Pos.CENTER_LEFT
		this.font = FontManager.boldFont(16.0)
		this.styleProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding "-fx-text-fill: ${
					ColorUtil.toHex(
						Settings.bgPrimary.value.invert()
					)
				};"
			}, property)
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