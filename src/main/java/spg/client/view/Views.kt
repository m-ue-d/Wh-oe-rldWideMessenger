package spg.client.view

import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.animation.ScaleTransition
import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ToggleGroup
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
import spg.client.model.Settings
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FlexSpacer
import java.lang.Math.pow
import java.nio.file.Path
import java.time.LocalDate
import java.util.Random
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.math.pow

class MainView : BorderPane() {
	init {
//		this.bottom = StatusBar()
		this.center = BorderPane().apply {
			this.center = ChatView()
			this.top = MenubarView()
		}
		this.left = SidebarView()
		this.right = UsersView()

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
	init {
		this.prefWidth = 200.0
		this.background = Background(
			BackgroundFill(
				Settings.bgSecondary.value,
//				CornerRadii(0.0, 20.0, 20.0, 0.0, false),
				CornerRadii.EMPTY,
				Insets.EMPTY
			)
		)
	}
}

class MenubarView : HBox() {
	private var xOffset: Double = 0.0
	private var yOffset: Double = 0.0
	init {
//		this.onMousePressed = EventHandler {
//			xOffset = it.sceneX
//			yOffset = it.sceneY
//		}
//
//		this.onMouseDragged = EventHandler {
//			this.scene.window.x = it.screenX - xOffset
//			this.scene.window.y = it.screenY - yOffset
//		}

		this.border = Border(
			BorderStroke(
				Color.TRANSPARENT,
				Color.TRANSPARENT,
				Settings.bgSecondary.value,
				Color.TRANSPARENT,
				BorderStrokeStyle.NONE,
				BorderStrokeStyle.NONE,
				BorderStrokeStyle.SOLID,
				BorderStrokeStyle.NONE,
				CornerRadii.EMPTY,
				BorderWidths(2.0),
				Insets(0.0, 60.0, 0.0, 60.0)
			)
		)

		this.padding = Insets(10.0, 60.0, 10.0, 60.0)
		this.spacing = 40.0
		this.prefHeight = 60.0
		this.alignment = Pos.CENTER_LEFT
		this.children.addAll(

		)
	}
}

class SidebarView : VBox() {
	val toggleGroup = ToggleGroup()
	init {
		this.border = Border(
			BorderStroke(
				Color.TRANSPARENT,
				Settings.bgSecondary.value,
				Color.TRANSPARENT,
				Color.TRANSPARENT,
				BorderStrokeStyle.NONE,
				BorderStrokeStyle.SOLID,
				BorderStrokeStyle.NONE,
				BorderStrokeStyle.NONE,
				CornerRadii.EMPTY,
				BorderWidths(2.0),
				Insets.EMPTY
			)
		)

		this.padding = Insets(10.0, 0.0, 10.0, 0.0)
		this.spacing = 40.0
		this.prefWidth = 50.0
		this.alignment = Pos.TOP_CENTER
		this.children.addAll(
			ImageView(
				Image("/spg/client/images/logo/messenger.png")
			).apply {
				this.fitWidth = 15.0
				this.fitHeight = 15.0
			},

			FlexSpacer(
				15.0, vBox = true
			),

			SidebarButton(
				Image("/spg/client/images/menu/settings.png"), BorderPane(), toggleGroup
			),
			SidebarButton(
				Image("/spg/client/images/menu/explore.png"), BorderPane(), toggleGroup
			),
			SidebarButton(
				Image("/spg/client/images/menu/messages.png"), BorderPane(), toggleGroup
			).apply {
				this.active.set(true)
			},

			FlexExpander(vBox = true),

			SidebarButton(
				Image("/spg/client/images/menu/addfriend.png"), BorderPane(), toggleGroup
			),
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

	class SidebarButton(img: Image, private val content: Node, private val toggleGroup : ToggleGroup?) : BorderPane() {
		val active: SimpleBooleanProperty = SimpleBooleanProperty(false)
		private val easeInOutBack = object : Interpolator() {
			override fun curve(t : Double) : Double {
				val c1 = 1.70158
				val c2 = c1 * 1.525

				return if (t < 0.5) {
					(2 * t).pow(2) * ((c2 + 1) * 2 * t - c2) / 2
				} else {
					(2 * t - 2).pow(2) * ((c2 + 1) * (t * 2 - 2) + c2) + 2
				} / 2
			}
		}

		init {
			toggleGroup?.add(this)
			this.padding = Insets(5.0)
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
						this.interpolator = easeInOutBack
					}.play()
					this@SidebarButton.border = Border(
						BorderStroke(
							Color.TRANSPARENT,
							Color.web("#0074f1"),
							Color.TRANSPARENT,
							Color.TRANSPARENT,
							BorderStrokeStyle.NONE,
							BorderStrokeStyle.SOLID,
							BorderStrokeStyle.NONE,
							BorderStrokeStyle.NONE,
							CornerRadii.EMPTY,
							BorderWidths(2.0),
							Insets.EMPTY
						)
					)
				} else {
					FadeTransition().apply {
						this.node = center
						this.fromValue = 1.0
						this.toValue = 0.5
						this.duration = Duration.seconds(0.4)
						this.interpolator = easeInOutBack
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
					this.interpolator = easeInOutBack
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
					this.interpolator = easeInOutBack
				}.play()
				RotateTransition().apply {
					this.node = center
					this.toAngle = 0.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}
			this.onMousePressed = EventHandler {
				active.set(true)
				toggleGroup?.toggle(this@SidebarButton)
				ScaleTransition().apply {
					this.node = center
					this.fromX = 1.3
					this.fromY = 1.3
					this.toX = 1.0
					this.toY = 1.0
					this.duration = Duration.seconds(0.5)
					this.interpolator = easeInOutBack
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
					this.interpolator = easeInOutBack
				}.play()
			}
		}
	}
}

class ChatView : BorderPane() {
	init {
		this.center = ChatPane()
	}

	class ChatPane : VBox() {
		init {
			this.padding = Insets(0.0, 60.0, 0.0, 60.0)
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
				ChatItem(userID, Label(message).apply {
					this.textFill = Settings.fontMain.value
					this.font = Font.loadFont(
						App::class.java.getResourceAsStream(
							"font/inter.ttf"
						), 14.0
					)
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
				Label(user.username).apply {
					this.textFill = Settings.fontMain.value
					this.font = Font.loadFont(
						App::class.java.getResourceAsStream(
							"font/inter.ttf"
						), 14.0
					)
					this.isWrapText = false
					this.prefHeight = 30.0
				},
				element
			).apply {
				this.padding = Insets(0.0, 0.0, 0.0, 15.0)
			}
		}
	}
}