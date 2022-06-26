package spg.client.view

import javafx.animation.*
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.scene.text.Font
import javafx.util.Duration
import spg.client.App
import spg.client.control.ClientNetwork
import spg.client.model.Current
import spg.client.model.Settings
import spg.client.view.utility.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.extension
import kotlin.io.path.name

class MainView : BorderPane() {
	companion object {
		private lateinit var centerViewNode: StackPane
		lateinit var usersViewNode: UsersView

		fun setCurrentView(view: Node) {
			if (!centerViewNode.children.contains(view)) {
				centerViewNode.children.add(
					view.apply {
						ScaleTransition().apply {
							this.node = view
							this.duration = Duration.seconds(0.5)
							this.fromX = 0.8
							this.fromY = 0.8
							this.toX = 1.0
							this.toY = 1.0
							this.interpolator = Interpolators.easeInOutBack
						}.play()
						FadeTransition().apply {
							this.node = view
							this.duration = Duration.seconds(0.5)
							this.fromValue = 0.0
							this.toValue = 1.0
							this.interpolator = Interpolator.EASE_BOTH
						}.play()
						PauseTransition().apply {
							this.duration = Duration.seconds(0.5)
							this.onFinished = EventHandler {
								centerViewNode.children.removeIf {
									centerViewNode.children.size > 1
								}
							}
						}.play()
					}
				)
			}
		}
	}
	init {
//		this.bottom = StatusBar()
		this.center = BorderPane().apply {
			this.center = StackPane(
				// current view
			).apply {
				centerViewNode = this
				this.padding = Insets(0.0, 20.0, 20.0, 0.0)
				setCurrentView(ChatView())
			}
			this.top = MenubarView()
		}
		this.left = SidebarView()
		this.right = UsersView().apply {
			usersViewNode = this
		}

		this.background = Background(
			BackgroundFill(
				Settings.bgMain.value,
//				CornerRadii(20.0),
				CornerRadii.EMPTY,
				Insets.EMPTY
			)
		)
	}
}

class UsersView : BorderPane() {
	private val panelWidth: Double = 200.0

	init {
		this.prefWidth = panelWidth
		this.background = Background(
			BackgroundFill(
				Settings.bgSecondary.value,
				CornerRadii.EMPTY,
				Insets.EMPTY
			)
		)
	}

	fun hide() {
		FadeTransition()
		AnyTransition {
			this.prefWidth = it
		}.apply {
			this.duration = Duration.seconds(0.5)
			this.from = this@UsersView.width
			this.to = 0.0
			this.interpolator = Interpolators.easeInOutBack
		}.play()
	}

	fun show() {
		FadeTransition()
		AnyTransition {
			this.prefWidth = it
		}.apply {
			this.duration = Duration.seconds(0.5)
			this.from = this@UsersView.width
			this.to = panelWidth
			this.interpolator = Interpolators.easeInOutBack
		}.play()
	}
}

class MenubarView : HBox() {
	init {
		this.padding = Insets(10.0, 20.0, 10.0, 20.0)
		this.spacing = 40.0
		this.prefHeight = 60.0
		this.alignment = Pos.CENTER_RIGHT
		this.children.addAll(
			HBox(
				FontManager.bold("", 24.0).apply {
					this.textProperty().bind(Current.panel)
				},
				// search bar
			).apply {
				this.alignment = Pos.CENTER
				this.spacing = 10.0
			},

			FlexExpander(
				hBox = true
			),

			HBox(
				Circle(15.0).apply {
					BorderPane.setAlignment(this, Pos.TOP_CENTER)
					this.fill = ImagePattern(
						Image("spg/client/images/Heinz.jpg")
					)
				},
				FontManager.regular("Heinz"),
				BorderPane(
					ImageView(
						Image("spg/client/images/misc/expand.png")
					).apply {
						this.opacity = 0.5
						this.fitWidth = 20.0
						this.fitHeight = 20.0
					},
				)
			).apply {
				this.alignment = Pos.CENTER
				this.spacing = 10.0
			}
		)
	}
}

class SidebarView : VBox() {
	private val toggleGroup = ToggleGroup()
	init {
		this.padding = Insets(20.0, 0.0, 10.0, 0.0)
		this.spacing = 20.0
		this.prefWidth = 50.0
		this.alignment = Pos.TOP_CENTER
		this.children.addAll(
			SidebarButton(
				Image("/spg/client/images/logo/messenger.png"), BorderPane(), toggleGroup
			) {
				MainView.usersViewNode.hide()
				Current.panel.set(Settings.title.get())
			}.apply {
				this.active.set(true)
			},

			FlexSpacer(
				15.0, vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/settings.png"), SettingsView(), toggleGroup
			) {
				MainView.usersViewNode.hide()
				Current.panel.set("Settings")
			},
			SidebarButton(
				Image("/spg/client/images/menu/explore.png"), BorderPane(), toggleGroup
			) {
				MainView.usersViewNode.show()
				Current.panel.set("Discover")
			},
			SidebarButton(
				Image("/spg/client/images/menu/messages.png"), BorderPane(), toggleGroup
			) {
				MainView.usersViewNode.show()
				Current.panel.set("Direct Messages")
			},
			SidebarButton(
				Image("/spg/client/images/menu/servers.png"), ChatView(), toggleGroup
			) {
				MainView.usersViewNode.show()
				Current.panel.set("Servers")
			},

			FlexExpander(
				vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/addfriend.png"), BorderPane(), toggleGroup
			) {
				MainView.usersViewNode.hide()
				Current.panel.set("Friends")
			},
		)
	}

	class ToggleGroup() {
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
		private val onAction: EventHandler<ActionEvent>? = null
	) : BorderPane() {
		val active: SimpleBooleanProperty = SimpleBooleanProperty(false)

		init {
			toggleGroup?.add(this)
			this.prefHeight = 50.0
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
						this.interpolator = Interpolators.easeInOutBack
					}.play()
					this@SidebarButton.border = Border(
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
				} else {
					FadeTransition().apply {
						this.node = center
						this.fromValue = 1.0
						this.toValue = 0.5
						this.duration = Duration.seconds(0.4)
						this.interpolator = Interpolators.easeInOutBack
					}.play()
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
					this.interpolator = Interpolators.easeInOutBack
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
					this.interpolator = Interpolators.easeInOutBack
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
					onAction?.handle(ActionEvent(
						this@SidebarButton, null
					))
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
					this.interpolator = Interpolators.easeInOutBack
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
					this.interpolator = Interpolators.easeInOutBack
				}.play()
			}
		}
	}
}

class SettingsView : BorderPane() {
	init {
		this.center = SettingsPane()
		this.background = Background(
			BackgroundFill(
				Settings.bgSecondary.value,
				CornerRadii(10.0),
				Insets.EMPTY
			)
		)
	}

	class SettingsPane : VBox() {

	}
}

class ChatView : BorderPane() {
	init {
		this.center = ChatPane()
		this.background = Background(
			BackgroundFill(
				Settings.bgSecondary.value,
				CornerRadii(10.0),
				Insets.EMPTY
			)
		)
	}

	class ChatPane : VBox() {
		init {
			this.padding = Insets(0.0, 10.0, 0.0, 10.0)
			addSpacing()
			addMessage(0, "Servus!")
		}

		fun addSpacing() {
			this.children.add(
				FlexSpacer(
					10.0, vBox = true
				)
			)
		}

		fun addMessage(userID: Int, message: String) {
			this.children.add(
				ChatItem(userID, FontManager.regular(message).apply {
					this.isWrapText = true
				})
			)
		}

		fun addImage(userID: Int, image: Image) {
			this.children.add(
				ChatItem(userID, ImageView(image).apply {
					this.fitWidth = 400.0
					this.fitHeight = 400.0 * image.height / image.width
				})
			)
		}

		fun addFile(userID: Int, file: Path) {
			this.children.add(
				ChatItem(userID, BorderPane().apply {
					this.left = ImageView(Image(
						file.extension.let {
							when (it) {
								"png" -> "/spg/client/images/files/image.png"
								"jpg" -> "/spg/client/images/files/image.png"
								"jpeg" -> "/spg/client/images/files/image.png"
								"gif" -> "/spg/client/images/files/image.png"
								"bmp" -> "/spg/client/images/files/image.png"
								"tiff" -> "/spg/client/images/files/image.png"
								"svg" -> "/spg/client/images/files/image.png"
								"webp" -> "/spg/client/images/files/image.png"

								"mp3" -> "/spg/client/images/files/audio.png"
								"wav" -> "/spg/client/images/files/audio.png"
								"flac" -> "/spg/client/images/files/audio.png"
								"aac" -> "/spg/client/images/files/audio.png"
								"ogg" -> "/spg/client/images/files/audio.png"

								"mp4" -> "/spg/client/images/files/movie.png"
								"avi" -> "/spg/client/images/files/movie.png"
								"mkv" -> "/spg/client/images/files/movie.png"
								"mov" -> "/spg/client/images/files/movie.png"
								"flv" -> "/spg/client/images/files/movie.png"

								"pdf" -> "/spg/client/images/files/pdf.png"

								"zip" -> "/spg/client/images/files/zip.png"
								"rar" -> "/spg/client/images/files/zip.png"
								"7z" -> "/spg/client/images/files/zip.png"
								"tar" -> "/spg/client/images/files/zip.png"
								"gz" -> "/spg/client/images/files/zip.png"
								"bz2" -> "/spg/client/images/files/zip.png"

								else -> "/spg/client/images/files/file.png"
							}
						}
					)).apply {
						this.fitWidth = 20.0
						this.fitHeight = 20.0
					}

					this.center = Label(file.name).apply {
						setAlignment(this, Pos.CENTER_LEFT)
						this.padding = Insets(0.0, 10.0, 0.0, 10.0)
					}

					this.right = ImageView(
						Image(
							"/spg/client/images/files/download.png"
						)
					).apply {
						this.fitWidth = 20.0
						this.fitHeight = 20.0
					}
				})
			)
		}
	}

	class ChatItem(userID: Int, element: Node) : BorderPane() {
		init {
			val user = ClientNetwork.getUser(userID)
			this.padding = Insets(10.0)
			this.left = Circle(15.0).apply {
				setAlignment(this, Pos.TOP_CENTER)
				this.fill = ImagePattern(
					user.avatar
				)
			}

			this.center = VBox(
				FontManager.regular(user.username).apply {
					this.isWrapText = false
					this.prefHeight = 30.0
				},  element
			).apply {
				this.padding = Insets(0.0, 0.0, 0.0, 15.0)
			}
		}
	}
}