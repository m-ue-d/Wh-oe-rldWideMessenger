package spg.client.view.utility

import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.animation.ScaleTransition
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.layout.StackPane
import javafx.util.Duration

object MultiStack {
	fun setStackPaneView(view: Node, pane: StackPane) {
		if (pane.children.contains(view)) {
			pane.children.remove(view)
		}

		pane.children.add(
			view.apply {
				ScaleTransition().apply {
					this.node = view
					this.duration = Duration.seconds(0.5)
					this.fromX = 0.8
					this.fromY = 0.8
					this.toX = 1.0
					this.toY = 1.0
					this.interpolator = Interpolator.easeInOutBack
				}.play()
				FadeTransition().apply {
					this.node = view
					this.duration = Duration.seconds(0.5)
					this.fromValue = 0.0
					this.toValue = 1.0
					this.interpolator = Interpolator.easeOut
				}.play()
				PauseTransition().apply {
					this.duration = Duration.seconds(0.5)
					this.onFinished = EventHandler {
						pane.children.removeIf { node ->
							node != pane.children.last()
						}
					}
				}.play()
			}
		)
	}
}