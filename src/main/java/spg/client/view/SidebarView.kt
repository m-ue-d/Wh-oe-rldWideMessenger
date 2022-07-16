package spg.client.view

import javafx.animation.FadeTransition
import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.BorderPane
import javafx.scene.layout.CornerRadii
import javafx.util.Duration
import spg.client.model.Settings
import spg.client.view.utility.AnyTransition

class SidebarView : BorderPane() {
	private val panelWidth: Double = 200.0

	init {
		this.prefWidth = panelWidth
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}

	fun hide() {
		FadeTransition()
		AnyTransition {
			this.prefWidth = it
		}.apply {
			this.duration = Duration.seconds(0.5)
			this.from = this@SidebarView.width
			this.to = 0.0
			this.interpolatorProperty().bind(
				Settings.easeInOutBack
			)
		}.play()
	}

	fun show() {
		FadeTransition()
		AnyTransition {
			this.prefWidth = it
		}.apply {
			this.duration = Duration.seconds(0.5)
			this.from = this@SidebarView.width
			this.to = panelWidth
			this.interpolatorProperty().bind(
				Settings.easeInOutBack
			)
		}.play()
	}
}

