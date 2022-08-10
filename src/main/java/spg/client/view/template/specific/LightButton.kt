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

class LightButton(text: String? = null, color: Color? = null, icon: Image? = null, onAction: EventHandler<MouseEvent>) : Button(text, color, icon, onAction) {
	init {
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				return@createObjectBinding Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii(5.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}
}