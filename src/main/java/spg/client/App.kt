package spg.client

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import javafx.stage.StageStyle
import spg.client.control.Client
import spg.client.control.ClientNetwork
import spg.client.model.Settings
import spg.client.view.MainView

fun main() {
	Application.launch(
		App::class.java
	)
}

class App : Application() {
	override fun start(stage: Stage) {
		ClientNetwork.initialize()
		Client("localhost", 8080)
			.start()

		stage.title = "${Settings.title.get()} - ${Settings.QUOTE}"
		stage.scene = Scene(MainView().apply {
		}, 1000.0, 600.0).apply {
			this.fill = Color.TRANSPARENT
			this.stylesheets.add(
				"spg/client/css/style.css"
			)
		}
		stage.show()
	}
}

