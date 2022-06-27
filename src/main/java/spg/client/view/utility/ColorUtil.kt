package spg.client.view.utility

import javafx.scene.paint.Color

class ColorUtil {
	companion object {
		fun toHex(c: Color) : String {
			return String.format( "#%02X%02X%02X",
				(c.red * 255).toInt(),
				(c.green * 255).toInt(),
				(c.blue * 255).toInt()
			)
		}

		fun toColor(hex: String) : Color {
			return if (hex.matches(Regex("#[\\da-fA-F]{6}")) || hex.matches(Regex("#[\\da-fA-F]{3}"))) {
				Color.web(hex)
			} else {
				Color.BLACK
			}
		}
	}
}