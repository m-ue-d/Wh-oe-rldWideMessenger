package spg.client

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import spg.client.control.ClientNetwork
import spg.client.model.Settings
import spg.client.view.MainView
import spg.client.view.WelcomeView

fun main() {
	Application.launch(
		App::class.java
	)
}

class App : Application() {
	companion object {
		lateinit var stage: Stage

		fun resize(to: Double) {
			val window = stage.scene.window
			val y = window.y
			val h = window.height
			window.height = to
			window.y = y + (h - to) / 2.0
		}
	}

	override fun start(s: Stage) {
		// try to auto-login first, if possible

		// BUG: the client network blocks the main thread, so the app doesn't start until the network is ready
		// this is a workaround for that, idk if putting it in a separate thread is a good idea
		Thread {
			ClientNetwork.initialize()
		}.start()

		if (Settings.account.value == null) {
			stage = s.apply {
				this.scene = Scene(WelcomeView(), 400.0, 450.0)
				this.isResizable = false
				this.show()
			}
		} else {
			stage = s.apply {
				this.titleProperty().bind(
					Settings.mainTitle.concat(" - ").concat(Settings.titleQuote)
				)
				this.scene = Scene(MainView(), 1000.0, 600.0).apply {
					this.stylesheets.add(
						"spg/client/css/style.css"
					)
				}
				this.show()
			}
		}
	}
}
