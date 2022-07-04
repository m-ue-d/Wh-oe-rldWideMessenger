package spg.client.view.template

import javafx.animation.Interpolator
import javafx.animation.TranslateTransition
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.client.model.Settings
import spg.client.view.utility.FontManager

class Button(text: String, color: Color, icon: Image, onAction: EventHandler<MouseEvent>) : HBox(
	ImageView(icon).apply {
		this.fitWidth = 20.0
		this.fitHeight = 20.0
	},
	FontManager.boldLabel(text, 16.0).apply {
		this.textFillProperty().unbind()
		this.textFill = color
	},
) {
	init {
		this.padding = Insets(10.0)
		this.spacing = 10.0
		this.prefHeight = 40.0
		this.alignment = Pos.CENTER_LEFT
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

		this.onMouseClicked = onAction

		this.onMouseEntered = EventHandler {
			TranslateTransition().apply {
				this.node = this@Button
				this.fromY = this@Button.translateY
				this.toY = -5.0
				this.duration = Duration.seconds(0.1)
				this.interpolator = Interpolator.EASE_OUT
			}.play()
		}

		this.onMouseExited = EventHandler {
			TranslateTransition().apply {
				this.node = this@Button
				this.fromY = this@Button.translateY
				this.toY = 0.0
				this.duration = Duration.seconds(0.1)
				this.interpolator = Interpolator.EASE_OUT
			}.play()
		}
	}
}