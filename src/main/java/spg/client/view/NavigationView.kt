package spg.client.view

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.animation.ScaleTransition
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.client.model.Current
import spg.client.model.Settings
import spg.client.view.template.ViewPane
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FlexSpacer
import java.util.*

class NavigationView : VBox() {
	private val toggleGroup = ToggleGroup()
	init {
		this.padding = Insets(10.0, 0.0, 20.0, 0.0)
		this.spacing = 20.0
		this.prefWidth = 50.0
		this.alignment = Pos.TOP_CENTER
		this.children.addAll(
			SidebarButton(
				Image("/spg/client/images/logo/messenger-hollow.png"), HomeView(), toggleGroup
			) {
				MainView.sidebarViewNode.hide()
				Current.panel.set("Welcome!")
			}.apply {
				this.active.set(true)
			},

			FlexSpacer(
				20.0, vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/messages.png"), ViewPane(), toggleGroup
			) {
				MainView.sidebarViewNode.show()
				Current.panel.set("Direct Messages")
			},
			SidebarButton(
				Image("/spg/client/images/menu/friends.png"), ViewPane(), toggleGroup
			) {
				MainView.sidebarViewNode.hide()
				Current.panel.set("Friends")
			},

			FlexSpacer(
				20.0, vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/servers.png"), ChatView(), toggleGroup
			) {
				MainView.sidebarViewNode.show()
				Current.panel.set("Servers")
			},
			SidebarButton(
				Image("/spg/client/images/menu/explore.png"), ViewPane(), toggleGroup
			) {
				MainView.sidebarViewNode.hide()
				Current.panel.set("Discover")
			},

			FlexExpander(
				vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/settings.png"), SettingsView(), toggleGroup
			) {
				MainView.sidebarViewNode.hide()
				Current.panel.set("Settings")
			},
		)
	}

	class ToggleGroup {
		private val buttons = mutableListOf<SidebarButton>()
		fun add(button: SidebarButton) {
			buttons.add(button)
		}
		fun toggle(button: SidebarButton) {
			for (b in buttons) {
				if (b == button) {
					b.active.set(true)
				} else {
					b.active.set(false)
				}
			}
		}
	}

	class SidebarButton(
		private val img: Image,
		private val content: Node,
		private val toggleGroup : ToggleGroup? = null,
		private val onAction: EventHandler<MouseEvent>? = null
	) : BorderPane() {
		val active: SimpleBooleanProperty = SimpleBooleanProperty(false)

		init {
			toggleGroup?.add(this)
			this.prefHeight = 40.0
			this.center = ImageView(img).apply {
				this.fitWidth = 15.0
				this.fitHeight = 15.0
				this.opacity = 0.5
			}

			active.addListener { _, _, v ->
				if (v) {
					FadeTransition().apply {
						this.node = center
						this.fromValue = 0.5
						this.toValue = 1.0
						this.duration = Duration.seconds(0.4)
						this.interpolatorProperty().bind(
							Settings.easeInOutBack
						)
					}.play()
					this@SidebarButton.borderProperty().bind(
						Bindings.createObjectBinding({
							Border(
								BorderStroke(
									Color.TRANSPARENT,
									Color.TRANSPARENT,
									Color.TRANSPARENT,
									Settings.colorAccent.value,
									BorderStrokeStyle.NONE,
									BorderStrokeStyle.NONE,
									BorderStrokeStyle.NONE,
									BorderStrokeStyle.SOLID,
									CornerRadii.EMPTY,
									BorderWidths(4.0),
									Insets.EMPTY,
								)
							)
						}, Settings.colorAccent)
					)
				} else {
					FadeTransition().apply {
						this.node = center
						this.fromValue = 1.0
						this.toValue = 0.5
						this.duration = Duration.seconds(0.4)
						this.interpolatorProperty().bind(
							Settings.easeInOutBack
						)
					}.play()
					this@SidebarButton.borderProperty().unbind()
					this@SidebarButton.border = null
				}
			}

			this.onMouseEntered = EventHandler {
				ScaleTransition().apply {
					this.node = center
					this.fromX = 1.0
					this.fromY = 1.0
					this.toX = 1.3
					this.toY = 1.3
					this.duration = Duration.seconds(0.5)
					this.interpolatorProperty().bind(
						Settings.easeInOutBack
					)
				}.play()
				RotateTransition().apply {
					this.node = center
					this.toAngle = Random().nextDouble() * 20.0 - 10.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}
			this.onMouseExited = EventHandler {
				ScaleTransition().apply {
					this.node = center
					this.fromX = 1.3
					this.fromY = 1.3
					this.toX = 1.0
					this.toY = 1.0
					this.duration = Duration.seconds(0.5)
					this.interpolatorProperty().bind(
						Settings.easeInOutBack
					)
				}.play()
				RotateTransition().apply {
					this.node = center
					this.toAngle = 0.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}
			this.onMousePressed = EventHandler {
				if (!active.value) {
					MainView.setCurrentView(content)
					onAction?.handle(it)
				}
				active.set(true)
				toggleGroup?.toggle(this@SidebarButton)
				ScaleTransition().apply {
					this.node = center
					this.fromX = 1.3
					this.fromY = 1.3
					this.toX = 1.0
					this.toY = 1.0
					this.duration = Duration.seconds(0.5)
					this.interpolatorProperty().bind(
						Settings.easeInOutBack
					)
				}.play()
			}
			this.onMouseReleased = EventHandler {
				ScaleTransition().apply {
					this.node = center
					this.fromX = 1.0
					this.fromY = 1.0
					this.toX = 1.3
					this.toY = 1.3
					this.duration = Duration.seconds(0.5)
					this.interpolatorProperty().bind(
						Settings.easeInOutBack
					)
				}.play()
			}
		}
	}
}