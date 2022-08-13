package spg.client.view.dialog

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import spg.client.control.network.ClientNetwork
import spg.client.model.Settings
import spg.client.view.MainView
import spg.client.view.template.Button
import spg.client.view.template.PortField
import spg.client.view.template.TextField
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FontManager

object ServerAddView : BorderPane() {
	private val ipInput: TextField
	private val portInput: TextField

	init {
		this.padding = Insets(50.0)
		this.top = HBox(
			FontManager.boldLabel(
				"Add a server", 20.0
			),

			FlexExpander(
				hBox = true
			),

			BorderPane(
				ImageView(
					Image("spg/client/images/misc/close.png")
				).apply {
					this.fitHeight = 24.0
					this.fitWidth = 24.0
				}
			).apply {
				this.cursor = Cursor.HAND
				this.onMouseClicked = EventHandler {
					closeView()
				}
			}
		).apply {
			this.padding = Insets(10.0)
			this.spacing = 10.0
			this.alignment = Pos.CENTER
		}

		this.center = VBox(
			TextField("IP Address").apply {
				ipInput = this
			},
			PortField("Port").apply {
				portInput = this
			},
			HBox(
				Button("Confirm", icon = Image(
					"spg/client/images/misc/confirm.png"
				)) {
					ClientNetwork.INSTANCE.tryAddServer(
						ipInput.text,
						portInput.text.toInt()
					)
					closeView()
				}.apply {
					HBox.setHgrow(this, Priority.ALWAYS)
					this.alignment = Pos.CENTER
				},

				Button("Test Connection", icon = Image(
					"spg/client/images/misc/test-connection.png"
				)) {
					ClientNetwork.INSTANCE.testConnection(
						ipInput.text,
						portInput.text.toInt()
					)
					closeView()
				}.apply {
					HBox.setHgrow(this, Priority.ALWAYS)
					this.alignment = Pos.CENTER
				}
			).apply {
				this.spacing = 10.0
			}
		).apply {
			this.padding = Insets(10.0, 50.0, 50.0, 50.0)
			this.spacing = 10.0
			this.alignment = Pos.CENTER
		}

		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.colors["Secondary Color"]!!.color.value,
						CornerRadii(10.0),
						Insets(40.0),
					)
				)
			}, Settings.colors["Secondary Color"]!!.color)
		)
	}

	private fun closeView() {
		MainView.removeView(BackGround, scale = false)
		MainView.removeView(ServerAddView, fade = false)
	}

	object BackGround : Pane() {
		init {
			this.background = Background(
				BackgroundFill(
					Color.web("rgba(21, 22, 24, 0.6)"),
					CornerRadii(10.0),
					Insets.EMPTY
				)
			)
		}
	}
}