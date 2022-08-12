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
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.client.model.Settings
import spg.client.view.utility.AnyTransition
import spg.client.view.utility.FontManager
import spg.client.view.utility.HoverTransition

open class Button(text: String? = null, color: Color? = null, icon: Image? = null, onAction: EventHandler<MouseEvent>) : HBox() {
	init {
		this.padding = Insets(10.0)
		this.spacing = 10.0
		this.prefHeight = 40.0
		this.alignment = Pos.CENTER_LEFT
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

		if (icon != null) {
			this.children.add(
				ImageView(icon).apply {
					this.fitWidth = 20.0
					this.fitHeight = 20.0
				}
			)
		}

		if (text != null) {
			this.children.add(
				FontManager.boldLabel(text, 16.0).apply {
					this.textFillProperty().unbind()
					this.textFill = color
				}
			)
		}

		this.onMouseClicked = onAction

		this.onMouseEntered = EventHandler {
			HoverTransition.onMouseEntered(this@Button)
		}

		this.onMouseExited = EventHandler {
			HoverTransition.onMouseExited(this@Button)
		}
	}
}