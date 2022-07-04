package spg.client.view

import javafx.beans.binding.Bindings
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.layout.Background
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import spg.client.App
import spg.client.model.Settings
import spg.client.view.utility.FontManager
import spg.client.view.template.Button
import spg.client.view.template.TextField
import spg.client.view.utility.FlexExpander

class SignupView : StackPane() {
	companion object {
		private lateinit var mainPane: StackPane

		fun showPane(node: Node) {
			mainPane.children.clear()
			mainPane.children.add(node)
		}
	}

	init {
		mainPane = this
		this.children.addAll(
			WelcomePane()
		)
	}

	class WelcomePane : VBox() {
		init {
			this.padding = Insets(30.0)
			this.spacing = 20.0
			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background.fill(Settings.bgSecondary.value)
				}, Settings.bgSecondary)
			)

			this.children.addAll(
				FontManager.boldLabel("Welcome to Whörld Wide Messenger!", 26.0).apply {
					this.isWrapText = true
				},

				FontManager.regularLabel("The messenger with revolutionary technology", 16.0).apply {
					this.isWrapText = true
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					Button("Log in", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/login.png"
					)) {
						App.resize(650.0)
						showPane(LoginPane())
					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Sign up", Color.web("#B8FFB7"), Image(
						"/spg/client/images/settings/signup.png"
					)) {
						App.resize(650.0)
						showPane(SignupPane())
					}.apply {
						this.alignment = Pos.CENTER
					}
				).apply {
					this.padding = Insets(30.0, 50.0, 30.0, 50.0)
					this.spacing = 22.0
				}
			)
		}
	}

	class LoginPane : VBox() {
		init {
			this.padding = Insets(30.0)
			this.spacing = 20.0
			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background.fill(Settings.bgSecondary.value)
				}, Settings.bgSecondary)
			)

			this.children.addAll(
				FontManager.boldLabel("Log into your account", 26.0).apply {
					this.isWrapText = true
				},

				FontManager.regularLabel("Type in your email address and your password.", 16.0).apply {
					this.isWrapText = true
					this.opacity = 0.5
				},

				FontManager.regularLabel("In case you forgot the password, you can reset it anytime.", 16.0).apply {
					this.isWrapText = true
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					TextField("Username"),

					TextField("Password"),

					Button("Reset your password", Color.web("#FFF176"), Image(
						"/spg/client/images/settings/resetpwd.png"
					)) {

					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Log in", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/login.png"
					)) {

					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Back", Color.web("#ECF0FF"), Image(
						"/spg/client/images/misc/back.png"
					)) {
						App.resize(450.0)
						showPane(WelcomePane())
					}.apply {
						this.alignment = Pos.CENTER
					}
				).apply {
					this.padding = Insets(30.0, 50.0, 30.0, 50.0)
					this.spacing = 22.0
				}
			)
		}
	}

	class SignupPane : VBox() {
		init {
			this.padding = Insets(30.0)
			this.spacing = 20.0
			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background.fill(Settings.bgSecondary.value)
				}, Settings.bgSecondary)
			)

			this.children.addAll(
				FontManager.boldLabel("Create a new account", 26.0).apply {
					this.isWrapText = true
				},

				FontManager.regularLabel("Tell us your name, email address and choose a strong password.", 16.0).apply {
					this.isWrapText = true
					this.opacity = 0.5
				},

				FontManager.regularLabel("You will be sent a verification code to your email address.", 16.0).apply {
					this.isWrapText = true
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					TextField("Username"),

					TextField("Email address"),

					TextField("Password"),

					Button("Sign up", Color.web("#B8FFB7"), Image(
						"/spg/client/images/settings/signup.png"
					)) {

					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Back", Color.web("#ECF0FF"), Image(
						"/spg/client/images/misc/back.png"
					)) {
						App.resize(450.0)
						showPane(WelcomePane())
					}.apply {
						this.alignment = Pos.CENTER
					}
				).apply {
					this.padding = Insets(30.0, 50.0, 30.0, 50.0)
					this.spacing = 22.0
				}
			)
		}
	}
}