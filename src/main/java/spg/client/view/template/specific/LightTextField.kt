package spg.client.view.template.specific

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import spg.client.model.Settings
import spg.client.view.template.Button
import spg.client.view.template.TextField

class LightTextField(prompt: String? = null) : TextField(prompt) {
	init {
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding Background(
					BackgroundFill(
						Settings.colors["Secondary Color"]!!.color.value,
						CornerRadii(5.0),
						Insets.EMPTY
					)
				)
			}, Settings.colors["Secondary Color"]!!.color)
		)
	}
}