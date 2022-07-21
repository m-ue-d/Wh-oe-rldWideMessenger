package spg.client.view.utility

import javafx.scene.control.Label
import javafx.scene.text.Font
import spg.client.App
import spg.client.model.Settings

class FontManager {
	companion object {
		fun regularFont(size: Double = 14.0) : Font {
			return Font.loadFont(
				App::class.java.getResourceAsStream(
					"font/inter-medium.ttf"
				), size
			)
		}

		fun regularLabel(text: String, size: Double = 14.0) : Label {
			return Label(text).apply {
				this.textFillProperty().bind(Settings.fontMain)
				this.font = regularFont(size)
				this.isWrapText = true
			}
		}

		fun boldFont(size: Double = 14.0) : Font {
			return Font.loadFont(
				App::class.java.getResourceAsStream(
					"font/poppins-bold.ttf"
				), size
			)
		}

		fun boldLabel(text: String, size: Double = 14.0) : Label {
			return Label(text).apply {
				this.textFillProperty().bind(Settings.fontMain)
				this.font = boldFont(size)
				this.isWrapText = true
			}
		}
	}
}