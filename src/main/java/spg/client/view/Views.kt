package spg.client.view

import javafx.animation.*
import javafx.beans.binding.Bindings
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.util.Duration
import javafx.util.StringConverter
import spg.client.control.ClientNetwork
import spg.client.model.Current
import spg.client.model.Settings
import spg.client.view.utility.*
import java.awt.Desktop
import java.net.URI
import java.nio.file.Path
import java.util.*
import kotlin.io.path.extension
import kotlin.io.path.name


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
		this.left = SidebarView()
		this.right = UsersView().apply {
			usersViewNode = this
		}

		setCurrentView(HomeView())
		usersViewNode.hide()
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
		lateinit var usersViewNode: UsersView

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

class UsersView : BorderPane() {
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
			this.from = this@UsersView.width
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
			this.from = this@UsersView.width
			this.to = panelWidth
			this.interpolatorProperty().bind(
				Settings.easeInOutBack
			)
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
				FontManager.boldLabel("", 24.0).apply {
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
				FontManager.regularLabel("Heinz"),
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
		this.padding = Insets(10.0, 0.0, 20.0, 0.0)
		this.spacing = 20.0
		this.prefWidth = 50.0
		this.alignment = Pos.TOP_CENTER
		this.children.addAll(
			SidebarButton(
				Image("/spg/client/images/logo/messenger-hollow.png"), HomeView(), toggleGroup
			) {
				MainView.usersViewNode.hide()
				Current.panel.set("Welcome!")
			}.apply {
				this.active.set(true)
			},

			FlexSpacer(
				20.0, vBox = true
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
				MainView.usersViewNode.hide()
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
						this.interpolatorProperty().bind(
							Settings.easeInOutBack
						)
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

class HomeView : BorderPane() {
	init {
		this.center = HomePane()
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii(10.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}

	class HomePane : HBox() {
		init {
			this.opacity = 0.3
			this.children.addAll(
				VBox(
					FontManager.boldLabel("Whörld Wide Messenger", 30.0),
					FlexSpacer(10.0, vBox = true),
					FontManager.regularLabel("The solution for your daily messenger.", 20.0).apply {
						this.isWrapText = true
					},
					FontManager.regularLabel("Simple, clean, and intuitive.", 20.0).apply {
						this.isWrapText = true
					},
					FontManager.regularLabel("Open Source with end-to-end encryption.", 20.0).apply {
						this.isWrapText = true
					},
					FlexSpacer(10.0, vBox = true),
					FontManager.regularLabel("Support us on social media:", 20.0).apply {
						this.isWrapText = true
					},
					FlexSpacer(20.0, vBox = true),
					HBox(
						SocialLink("GitHub", Image(
							"/spg/client/images/social/github.png",
						), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger")),

						SocialLink("GitHub", Image(
							"/spg/client/images/social/github.png",
						), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger")),

						SocialLink("GitHub", Image(
							"/spg/client/images/social/github.png",
						), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger"))
					).apply {
						this.alignment = Pos.CENTER_LEFT
						this.spacing = 20.0
					}
				).apply {
					this.spacing = 10.0
					this.prefWidth = 400.0
					this.alignment = Pos.CENTER_LEFT
				},

				ImageView(
					Image("/spg/client/images/logo/messenger-white.png")
				).apply {
					this.opacity = 0.4
					this.fitWidth = 200.0
					this.fitHeight = 200.0
				}
			)

			this.alignment = Pos.CENTER
			this.spacing = 50.0
		}
	}

	class SocialLink(private val name: String, private val img: Image, private val link: URI) : BorderPane() {
		init {
			this.center = ImageView(img).apply {
				this.fitWidth = 30.0
				this.fitHeight = 30.0
			}
			this.bottom = FontManager.boldLabel(name, 13.0).apply {
				this.isWrapText = true
				this.padding = Insets(5.0)
			}
			this.onMouseClicked = EventHandler {
				Desktop.getDesktop().browse(link)
			}
		}
	}
}

class SettingsView : BorderPane() {
	init {
		this.padding = Insets(10.0)
		this.center = SettingsPane()
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii(10.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}

	class SettingsPane : VBox(
		SettingsGroup("Appearance"),
		SettingsItem(
			"Primary Color",
			"The primary color of the application. It is the darkest color of all.",
			SettingsItem.ColorField(Settings.bgPrimary)
		),
		SettingsItem(
			"Secondary Color",
			"A slightly lighter variant of the primary color. Used for elevated elements.",
			SettingsItem.ColorField(Settings.bgSecondary)
		),
		SettingsItem(
			"Tertiary Color",
			"The lightest color in the application. Used for backgrounds and light fonts.",
			SettingsItem.ColorField(Settings.bgTertiary)
		),
		SettingsItem(
			"Accent Color",
			"A colorful alternative to the other colors. Mainly used for tab indicators.",
			SettingsItem.ColorField(Settings.colorAccent)
		),
		SettingsItem(
			"Font Color",
			"The main color of the application's font. Usually set to a color near white.",
			SettingsItem.ColorField(Settings.fontMain)
		),
	) {
		init {
			this.spacing = 10.0
		}
	}

	class SettingsGroup(private val title: String) : HBox() {
		init {
			this.alignment = Pos.CENTER
			this.padding = Insets(20.0)
			this.spacing = 10.0
			this.children.addAll(
				Separator(Orientation.HORIZONTAL).apply {
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							return@createObjectBinding Background.fill(
								Settings.bgTertiary.value
							)
						}, Settings.bgTertiary)
					)
					HBox.setHgrow(this, Priority.ALWAYS)
				},
				FontManager.boldLabel(title, 16.0).apply {
					this.textFillProperty().bind(
						Settings.bgTertiary
					)
				},
				Separator(Orientation.HORIZONTAL).apply {
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							return@createObjectBinding Background.fill(
								Settings.bgTertiary.value
							)
						}, Settings.bgTertiary)
					)
					HBox.setHgrow(this, Priority.ALWAYS)
				}
			)
		}
	}

	class SettingsItem(private val title: String, private val description: String, private val setting: Region) : HBox() {
		private val hoverOpacity = SimpleDoubleProperty(0.0)
		init {
			this.alignment = Pos.CENTER
			this.padding = Insets(10.0, 20.0, 10.0, 20.0)
			this.children.addAll(
				VBox(
					FontManager.boldLabel(title, 16.0).apply {
						this.textFillProperty().bind(
							Settings.fontMain
						)
					},
					FontManager.regularLabel(description, 14.0).apply {
						this.prefWidth = 300.0
						this.isWrapText = true
						this.textFillProperty().bind(
							Settings.bgTertiary
						)
					}
				).apply {
					this.spacing = 10.0
				},

				FlexExpander(
					hBox = true
				),

				setting.apply {
					this.prefHeight = 40.0
					this.border = null
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
				}
			)

			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					return@createObjectBinding Background(
						BackgroundFill(
							Settings.bgTertiary.value.deriveColor(
								0.0, 1.0, 1.0, hoverOpacity.value
							),
							CornerRadii(7.0),
							Insets.EMPTY
						)
					)
				}, Settings.bgTertiary, hoverOpacity)
			)

			this.onMouseEntered = EventHandler {
				AnyTransition {
					hoverOpacity.set(it)
				}.apply {
					this.from = hoverOpacity.value
					this.to = 0.3
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}

			this.onMouseExited = EventHandler {
				AnyTransition {
					hoverOpacity.set(it)
				}.apply {
					this.from = hoverOpacity.value
					this.to = 0.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}
		}

		class ColorField(private val property: ObjectProperty<Color>) : TextField() {
			init {
				this.alignment = Pos.CENTER_LEFT
				this.deselect()
				this.font = FontManager.boldFont(16.0)
				this.styleProperty().bind(
					Bindings.createObjectBinding({
						return@createObjectBinding "-fx-text-fill: ${ColorUtil.toHex(
							Settings.bgPrimary.value.invert()
						)};"
					}, property)
				)
				Bindings.bindBidirectional(
					this.textProperty(),
					property,
					object : StringConverter<Color>() {
						override fun toString(obj : Color) : String {
							return ColorUtil.toHex(obj)
						}

						override fun fromString(str : String) : Color {
							return ColorUtil.toColor(str)
						}
					}
				)
			}
		}
	}
}

class ChatView : BorderPane() {
	init {
		this.center = ChatPane()
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii(10.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}

	class ChatPane : VBox() {
		init {
			this.padding = Insets(10.0)
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
				ChatItem(userID, FontManager.regularLabel(message).apply {
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
				FontManager.boldLabel(user.username).apply {
					this.isWrapText = false
					this.prefHeight = 30.0
				},  element
			).apply {
				this.padding = Insets(0.0, 0.0, 0.0, 15.0)
			}
		}
	}
}