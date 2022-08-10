package spg.client.view.template.specific

import spg.client.view.template.NumberField

class PortField(prompt: String? = null) : NumberField(prompt) {
	init {
		this.textProperty().addListener { _, _, v ->
			if (v.isNotEmpty()) {
				this.text = if (v.toInt() !in 0..65535) "65535" else v
			}
		}
	}
}