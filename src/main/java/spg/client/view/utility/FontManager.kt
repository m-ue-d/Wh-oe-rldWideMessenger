package spg.client.view.utility

import javafx.scene.control.Label
import javafx.scene.text.Font
import spg.client.App
import spg.client.model.Settings

class FontManager {
	companion object {
		fun regular(text: String, size: Double = 14.0) : Label {
			return Label(text).apply {
				this.textFill = Settings.fontMain.value
				this.font = Font.loadFont(
					App::class.java.getResourceAsStream(
						"font/inter-medium.ttf"
					), size
				)
			}
		}

		fun bold(text: String, size: Double = 14.0) : Label {
			return Label(text).apply {
				this.textFill = Settings.fontMain.value
				this.font = Font.loadFont(
					App::class.java.getResourceAsStream(
						"font/poppins-bold.ttf"
					), size
				)
			}
		}
	}
}