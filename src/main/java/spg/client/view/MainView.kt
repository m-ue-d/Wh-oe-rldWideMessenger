package spg.client.view

import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.layout.*
import spg.client.model.Current
import spg.client.model.Settings
import spg.client.view.utility.MultiStack

object MainView : BorderPane() {

	private var stack: StackPane

	init {
		this.center = BorderPane().apply {
			this.top = MenubarView
			this.center = StackPane(
				// <current view>
			).apply {
				stack = this
				this.padding = Insets(0.0, 20.0, 20.0, 0.0)
			}
		}
		this.left = NavigationView
		this.right = SidebarView

		setView(HomeView)
		SidebarView.hide()
		Current.panel.set("Welcome!")

		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.colors["Primary Color"]!!.color.value,
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)
			}, Settings.colors["Primary Color"]!!.color)
		)
	}

	fun setView(view: Node) {
		MultiStack.setStackPaneView(view, stack)
	}
}