package spg.client.view.template

open class NumberField(prompt: String? = null) : TextField(prompt) {
	init {
		this.textProperty().addListener { _, _, v ->
			if (v.isNotBlank()) {
				this.text = v.replace(Regex("[^0-9]"), "")
			}
		}
	}
}