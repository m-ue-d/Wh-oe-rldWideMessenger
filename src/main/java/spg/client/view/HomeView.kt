package spg.client.view

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import spg.client.view.template.ViewPane
import spg.client.view.utility.FlexSpacer
import spg.client.view.utility.FontManager
import java.awt.Desktop
import java.net.URI

class HomeView : ViewPane() {
	init {
		this.center = HomePane()
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
						SocialLink(
							"GitHub", Image(
								"/spg/client/images/social/github.png",
							), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger")
						),

						SocialLink(
							"GitHub", Image(
								"/spg/client/images/social/github.png",
							), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger")
						),

						SocialLink(
							"GitHub", Image(
								"/spg/client/images/social/github.png",
							), URI("https://github.com/m-ue-d/Wh-oe-rldWideMessenger")
						)
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