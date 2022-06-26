package spg.client.view

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import spg.client.control.Client
import spg.client.control.ClientNetwork

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

		stage.title = "Whörld Wide Messenger"
		stage.scene = Scene(MainView(), 1000.0, 600.0)
		stage.show()
	}
}

class MainView : BorderPane() {
	init {
		this.center = ChatView()
		this.left = UsersView()
	}
}