package spg.client.view.sidebar

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.util.Callback
import spg.client.control.network.ClientNetwork
import spg.client.model.Current
import spg.client.model.ServerListItem
import spg.client.model.Settings
import spg.client.view.template.Button
import spg.client.view.template.TextField
import spg.client.view.template.specific.PortField
import spg.client.view.utility.FontManager
import spg.client.view.utility.HoverTransition

object ServerSidebarView : VBox() {
	private val addressInput: TextField
	private val portInput: TextField

	init {
		this.spacing = 10.0
		this.children.addAll(
			ScrollPane(
				ListView<ServerListItem>().apply {
					this.items = Settings.servers
					this.cellFactory = Callback {
						ServerListCell()
					}
					this.selectionModel.selectedItemProperty().addListener { _, _, v ->
						if (v != null) {
							Current.server.set(v)
						}
					}
				},
			).apply {
				setVgrow(this, Priority.ALWAYS)
				this.isFitToWidth = true
				this.background = Background.EMPTY
			},

			VBox(
				TextField("Address").apply {
					addressInput = this
				},

				HBox(
					PortField("Port").apply {
						portInput = this
					}, Button(icon = Image(
						"/spg/client/images/misc/add.png"
					)) {
						ClientNetwork.INSTANCE.tryAddServer(
							addressInput.text, portInput.text.toInt()
						)
					}
				).apply {
					this.spacing = 10.0
				}
			).apply {
				this.spacing = 10.0
			}
		)

		Settings.servers.addAll(
			ServerListItem("Other Server", "other.example.com", 8080),
			ServerListItem("My Server", "localhost", 25565),
			ServerListItem("Friend's Server", "friend.example.com", 1982),
		)
	}

	class ServerListCell : ListCell<ServerListItem>() {
		var active = false

		override fun updateItem(item : ServerListItem?, empty : Boolean) {
			super.updateItem(item, empty)
			if (item != null) {
				graphic = HBox(
					ImageView(
						Image("spg/client/images/misc/server.png")
					).apply {
						this.fitHeight = 25.0
						this.fitWidth = 25.0
					},

					VBox(
						FontManager.boldLabel(
							item.getName()
						),
						FontManager.regularLabel(
							item.getIp() + " : " + item.getPort()
						).apply {
							this.font = FontManager.regularFont(11.0)
						},
					)
				).apply {
					this.alignment = Pos.CENTER_LEFT
					this.spacing = 10.0
					this.padding = Insets(15.0)
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							Background(
								BackgroundFill(
									Settings.bgPrimary.value,
									CornerRadii(10.0),
									Insets.EMPTY
								)
							)
						}, Settings.bgPrimary)
					)

					this.onMouseEntered = EventHandler {
						HoverTransition.onMouseEntered(this@ServerListCell)
					}

					this.onMouseExited = EventHandler {
						HoverTransition.onMouseExited(this@ServerListCell)
					}
				}
			}
		}
	}
}