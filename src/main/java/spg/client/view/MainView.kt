package spg.client.view

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.PauseTransition
import javafx.animation.ScaleTransition
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.layout.*
import javafx.util.Duration
import spg.client.model.Current
import spg.client.model.Settings

class MainView : BorderPane() {
	init {
//		this.bottom = StatusBar()
		this.center = BorderPane().apply {
			this.center = StackPane(
				// current view
			).apply {
				centerViewNode = this
				this.padding = Insets(0.0, 20.0, 20.0, 0.0)
			}
			this.top = MenubarView()
		}
		this.left = NavigationView()
		this.right = SidebarView().apply {
			sidebarViewNode = this
		}

		setCurrentView(HomeView())
		sidebarViewNode.hide()
		Current.panel.set("Welcome!")

		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgPrimary.value,
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)
			}, Settings.bgPrimary)
		)
	}

	companion object {
		private lateinit var centerViewNode: StackPane
		lateinit var sidebarViewNode: SidebarView

		fun setCurrentView(view: Node) {
			if (centerViewNode.children.contains(view)) {
				centerViewNode.children.remove(view)
			}

			centerViewNode.children.add(
				view.apply {
					ScaleTransition().also {
						it.node = this
						it.duration = Duration.seconds(0.5)
						it.fromX = 0.8
						it.fromY = 0.8
						it.toX = 1.0
						it.toY = 1.0
						it.interpolatorProperty().bind(
							Settings.easeInOutBack
						)
					}.play()
					FadeTransition().also {
						it.node = this
						it.duration = Duration.seconds(0.5)
						it.fromValue = 0.0
						it.toValue = 1.0
						it.interpolator = Interpolator.EASE_BOTH
					}.play()
					PauseTransition().also {
						it.duration = Duration.seconds(0.5)
						it.onFinished = EventHandler {
							centerViewNode.children.removeIf { node ->
								node != centerViewNode.children.last()
							}
						}
					}.play()
				}
			)
		}
	}
}