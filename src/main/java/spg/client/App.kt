package spg.client

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.stage.Stage
import spg.client.control.network.ClientNetwork
import spg.client.model.Settings
import spg.client.view.MainView
import spg.client.view.WelcomeView

fun main() {
	System.err.println("Client should be started from the Client class!")
	ClientGui.initialize()
}

object ClientGui {
	fun initialize() {
		Thread {
			println("Initializing graphical user interface...")
			Application.launch(
				App::class.java
			)
		}.start()
	}
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

		fun welcome(s: Stage) {
			stage = s.apply {
				this.titleProperty().bind(Settings.welcomeTitle)
				this.scene = Scene(WelcomeView(), 400.0, 450.0)
				this.isResizable = false
				this.show()
			}
		}

		fun chatting(s: Stage) {
			stage = s.apply {
				this.centerOnScreen() // BUG: Not working
				this.isResizable = true
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

	override fun start(s: Stage) {
		Settings.account.addListener { _, _, newValue ->
			when (newValue) {
				null -> {
					welcome(s)
				}

				else -> {
					chatting(s)
				}
			}
		}

		welcome(s)

		s.onCloseRequest = EventHandler {
			ClientNetwork.INSTANCE.shutdown()
		}
	}
}
