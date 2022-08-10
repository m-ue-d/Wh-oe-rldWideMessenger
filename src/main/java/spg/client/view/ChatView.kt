package spg.client.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import spg.client.view.template.ViewPane
import spg.client.view.utility.FlexSpacer
import spg.client.view.utility.FontManager
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.name

object ChatView : ViewPane() {
	init {
		this.center = ChatPane
	}

	object ChatPane : VBox() {
		init {
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

	class ChatItem(uid: Int, element: Node) : BorderPane() {
		init {
			this.padding = Insets(10.0)
			this.left = Circle(15.0).apply {
				setAlignment(this, Pos.TOP_CENTER)
				this.fill = ImagePattern(
					Image(
						"spg/server/database/avatars/0.png"
					)
				)
			}

			this.center = VBox(
				FontManager.boldLabel("Heinz").apply {
					this.isWrapText = false
					this.prefHeight = 30.0
				}, element
			).apply {
				this.padding = Insets(0.0, 0.0, 0.0, 15.0)
			}
		}
	}
}