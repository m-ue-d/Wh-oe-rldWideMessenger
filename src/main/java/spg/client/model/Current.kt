package spg.client.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import spg.client.model.Settings.ServerListItem

object Current {
	val panel = SimpleStringProperty(
		Internal.mainTitle.get()
	)

	val server = SimpleObjectProperty<ServerListItem?>(
		null
	)

	val channel = SimpleIntegerProperty(
		-1
	)
}