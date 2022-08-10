package spg.client.view

import javafx.animation.FadeTransition
import javafx.animation.ScaleTransition
import javafx.beans.binding.Bindings
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import spg.client.control.network.ClientNetwork
import spg.client.model.Settings
import spg.client.view.template.Button
import spg.client.view.template.TextField
import spg.client.view.template.specific.LightButton
import spg.client.view.template.specific.LightTextField
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FlexItem
import spg.client.view.utility.FontManager
import spg.client.view.utility.Interpolator
import spg.shared.utility.Validator

object WelcomeView : StackPane() {
	fun setPane(node: Node) {
		this.children.clear()
		this.children.add(node)
	}

	fun addPane(node: Node) {
		this.children.add(node)
	}

	fun removePane(node: Node) {
		this.children.remove(node)
	}

	init {
		this.children.addAll(
			WelcomePane
		)
	}

	object WelcomePane : VBox() {
		init {
			this.padding = Insets(30.0)
			this.spacing = 20.0
			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background.fill(Settings.bgSecondary.value)
				}, Settings.bgSecondary)
			)

			this.children.addAll(
				FontManager.boldLabel("Welcome to Heast Messenger!", 26.0),

				FontManager.regularLabel("The messenger with revolutionary technology", 16.0).apply {
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					Button("Log in", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/login.png"
					)) {
						ClientGui.resize(700.0)
						setPane(LoginPane)
					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Sign up", Color.web("#B8FFB7"), Image(
						"/spg/client/images/settings/signup.png"
					)) {
						ClientGui.resize(650.0)
						setPane(SignupPane)
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

	object LoginPane : VBox() {
		private val emailField : TextField
		private val passwordField : TextField
		init {
			this.padding = Insets(30.0)
			this.spacing = 20.0
			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background.fill(Settings.bgSecondary.value)
				}, Settings.bgSecondary)
			)

			this.children.addAll(
				FontManager.boldLabel("Log into your account", 26.0),

				FontManager.regularLabel("Type in your email address and your password.", 16.0).apply {
					this.opacity = 0.5
				},

				FontManager.regularLabel("In case you forgot the password, you can reset it anytime. Just type in your new password into the password field.", 16.0).apply {
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					TextField("Email address").apply {
						this@LoginPane.emailField = this
					},

					TextField("Password").apply {
						this@LoginPane.passwordField = this
					},

					Button("Reset your password", Color.web("#FFF176"), Image(
						"/spg/client/images/settings/resetpwd.png"
					)) {
						ClientNetwork.INSTANCE.reset(
							emailField.text, passwordField.text
						)
					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Log in", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/login.png"
					)) {
						ClientNetwork.INSTANCE.login(
							emailField.text,
							passwordField.text
						)
					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Back", Color.web("#ECF0FF"), Image(
						"/spg/client/images/misc/back.png"
					)) {
						ClientGui.resize(450.0)
						setPane(WelcomePane)
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

	object SignupPane : VBox() {
		private val usernameField : TextField
		private val emailField : TextField
		private val passwordField : TextField

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
				},

				FontManager.regularLabel("Tell us your name, email address and choose a strong password.", 16.0).apply {
					this.opacity = 0.5
				},

				FontManager.regularLabel("You will be sent a verification code to your email address.", 16.0).apply {
					this.opacity = 0.5
				},

				FlexExpander(
					vBox = true
				),

				VBox(
					TextField("Username").apply {
						this@SignupPane.usernameField = this
					},

					TextField("Email address").apply {
						this@SignupPane.emailField = this
					},

					TextField("Password").apply {
						this@SignupPane.passwordField = this
					},

					Button("Sign up", Color.web("#B8FFB7"), Image(
						"/spg/client/images/settings/signup.png"
					)) {
						ClientNetwork.INSTANCE.signup(
							usernameField.text,
							emailField.text,
							passwordField.text
						)
					}.apply {
						this.alignment = Pos.CENTER
					},

					Button("Back", Color.web("#ECF0FF"), Image(
						"/spg/client/images/misc/back.png"
					)) {
						ClientGui.resize(450.0)
						setPane(WelcomePane)
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

	object VerificationPane : VBox() {
		private val verificationField : TextField

		init {
			FadeTransition().apply {
				this.node = this@VerificationPane
				this.fromValue = 0.0
				this.toValue = 1.0
				this.duration = Duration.seconds(0.5)
			}.play()

			ScaleTransition().apply {
				this.node = this@VerificationPane
				this.fromX = 0.8
				this.fromY = 0.8
				this.toX = 1.0
				this.toY = 1.0
				this.duration = Duration.seconds(1.0)
				this.interpolator = Interpolator.easeInOutBack
			}.play()

			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					Background(
						BackgroundFill(
							Settings.bgPrimary.value,
							CornerRadii(10.0),
							Insets(30.0)
						)
					)
				}, Settings.bgPrimary)
			)

			this.padding = Insets(50.0)
			this.spacing = 20.0
			this.alignment = Pos.CENTER
			this.children.addAll(
				FlexExpander(
					vBox = true
				),

				FontManager.boldLabel("Verify your account", 26.0),

				FontManager.regularLabel("A verification code was just sent to your email address.", 16.0).apply {
					this.opacity = 0.5
				},

				FontManager.regularLabel("Type it in and click continue if you're ready.", 16.0).apply {
					this.opacity = 0.5
				},

				FlexItem(
					vBox = true
				),

				HBox(
					LightTextField("Verification Code").apply {
						this@VerificationPane.verificationField = this
					},

					LightButton(icon = Image(
						"/spg/client/images/misc/done.png"
					)) {
						ClientNetwork.INSTANCE.verify(
							verificationField.text.uppercase()
						)
					}.apply {
						this.alignment = Pos.CENTER
					}
				).apply {
					this.alignment = Pos.CENTER
					this.spacing = 10.0
				},

				FlexExpander(
					vBox = true
				),

				LightButton("Back", Color.web("#ECF0FF"), Image(
					"/spg/client/images/misc/back.png"
				)) {
					removePane(this@VerificationPane)
				}.apply {
					this.alignment = Pos.CENTER
				}
			)
		}
	}
}