package spg.client.view

import javafx.animation.Interpolator
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.util.Duration
import spg.client.model.Settings
import spg.client.view.template.Button
import spg.client.view.template.ColorField
import spg.client.view.template.ViewPane
import spg.client.view.utility.*
import java.time.format.DateTimeFormatter

class SettingsView : ViewPane() {
	init {
		this.center = ScrollPane(
			SettingsPane()
		).apply {
			this.isFitToWidth = true
			this.background = Background.fill(Color.TRANSPARENT)
		}
	}

	class SettingsPane : VBox(
		SettingsGroup("Account"),
		AccountArea(),

		SettingsGroup("Appearance"),
		SettingsItem(
			"Primary Color",
			"The primary color of the application. It is the darkest color of all.",
			ColorField(Settings.bgPrimary)
		),
		SettingsItem(
			"Secondary Color",
			"A slightly lighter variant of the primary color. Used for elevated elements.",
			ColorField(Settings.bgSecondary)
		),
		SettingsItem(
			"Tertiary Color",
			"The lightest color in the application. Used for backgrounds and light fonts.",
			ColorField(Settings.bgTertiary)
		),
		SettingsItem(
			"Accent Color",
			"A colorful alternative to the other colors. Mainly used for tab indicators.",
			ColorField(Settings.colorAccent)
		),
		SettingsItem(
			"Font Color",
			"The main color of the application's font. Usually set to a color close to white.",
			ColorField(Settings.fontMain)
		),
	) {
		init {
			this.spacing = 10.0
		}
	}

	class SettingsGroup(title: String) : HBox() {
		init {
			this.alignment = Pos.CENTER
			this.padding = Insets(20.0)
			this.spacing = 10.0
			this.children.addAll(
				Separator(Orientation.HORIZONTAL).apply {
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							return@createObjectBinding Background.fill(
								Settings.bgTertiary.value
							)
						}, Settings.bgTertiary)
					)
					setHgrow(this, Priority.ALWAYS)
				},
				FontManager.boldLabel(title, 16.0).apply {
					this.textFillProperty().bind(
						Settings.bgTertiary
					)
				},
				Separator(Orientation.HORIZONTAL).apply {
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							return@createObjectBinding Background.fill(
								Settings.bgTertiary.value
							)
						}, Settings.bgTertiary)
					)
					setHgrow(this, Priority.ALWAYS)
				}
			)
		}
	}

	class SettingsItem(title: String, description: String, setting: Region) : HBox() {
		private val hoverOpacity = SimpleDoubleProperty(0.0)
		init {
			this.alignment = Pos.CENTER
			this.padding = Insets(10.0, 20.0, 10.0, 20.0)
			this.spacing = 10.0
			this.children.addAll(
				VBox(
					FontManager.boldLabel(title, 16.0).apply {
						this.textFillProperty().bind(
							Settings.fontMain
						)
					},
					FontManager.regularLabel(description, 14.0).apply {
						this.prefWidth = 300.0
						this.isWrapText = true
						this.textFillProperty().bind(
							Settings.bgTertiary
						)
					}
				).apply {
					this.spacing = 10.0
				},

				FlexExpander(
					hBox = true
				),

				setting.apply {
					this.prefHeight = 40.0
					this.border = null
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							return@createObjectBinding Background(
								BackgroundFill(
									Settings.bgPrimary.value,
									CornerRadii(5.0),
									Insets.EMPTY
								)
							)
						}, Settings.bgPrimary)
					)
				}
			)

			this.backgroundProperty().bind(
				Bindings.createObjectBinding({
					return@createObjectBinding Background(
						BackgroundFill(
							Settings.bgTertiary.value.deriveColor(
								0.0, 1.0, 1.0, hoverOpacity.value
							),
							CornerRadii(7.0),
							Insets.EMPTY
						)
					)
				}, Settings.bgTertiary, hoverOpacity)
			)

			this.onMouseEntered = EventHandler {
				AnyTransition {
					hoverOpacity.set(it)
				}.apply {
					this.from = hoverOpacity.value
					this.to = 0.3
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}

			this.onMouseExited = EventHandler {
				AnyTransition {
					hoverOpacity.set(it)
				}.apply {
					this.from = hoverOpacity.value
					this.to = 0.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.EASE_OUT
				}.play()
			}
		}
	}

	class AccountArea : HBox() {
		init {
			this.padding = Insets(10.0, 20.0, 10.0, 20.0)
			this.children.addAll(
				VBox(
					HBox(
						Circle(75.0).apply {
							this.fillProperty().bind(
								Bindings.createObjectBinding({
									return@createObjectBinding ImagePattern(
										// Image(Settings.account.value?.img ?: "/path/to/default/img.png")
										Image("spg/server/database/avatars/0.png")
									)
								}, Settings.account)
							)
						},
						VBox(
							FontManager.boldLabel("", 20.0).apply {
								this.textProperty().bind(
									Bindings.createObjectBinding({
										return@createObjectBinding Settings.account.value?.uname
											?: "Not logged in"
									}, Settings.account)
								)
							},

							FlexItem(vBox = true),

							HBox(
								FontManager.regularLabel("email: ", 16.0),
								FontManager.boldLabel("", 16.0).apply {
									this.textProperty().bind(
										Bindings.createObjectBinding({
											val email = Settings.account.value?.email
												?: "guest@wwm"
											return@createObjectBinding "${
												email.substring(0..4)
											}[...]${
												email.substring(email.length - 4)
											}"
										}, Settings.account)
									)
								}
							),

							HBox(
								FontManager.regularLabel("password: ", 16.0),
								FontManager.boldLabel("**********", 16.0)
							),

							HBox(
								FontManager.regularLabel("member since: ", 16.0),
								FontManager.boldLabel("", 16.0).apply {
									this.textProperty().bind(
										Bindings.createObjectBinding({
											return@createObjectBinding Settings.account.value?.since?.format(
												DateTimeFormatter.ofPattern("dd. MMM. yyyy")
											) ?: "Unknown"
										}, Settings.account)
									)
								}
							)
						).apply {
							this.padding = Insets(20.0)
							this.spacing = 10.0
						}
					).apply {
						this.alignment = Pos.CENTER_LEFT
					},

					VBox(
						FontManager.regularLabel("Account Information:", 16.0).apply {
							this.isWrapText = true
						},

						FlexSpacer(5.0, vBox = true),

						HBox(
							FontManager.regularLabel(" • uid: ", 16.0),
							FontManager.boldLabel("", 16.0).apply {
								this.textProperty().bind(
									Bindings.createObjectBinding({
										return@createObjectBinding Settings.account.value?.id?.toString()
											?: "Unknown"
									}, Settings.account)
								)
							}
						),

						HBox(
							FontManager.regularLabel(" • key: ", 16.0),
							FontManager.boldLabel("", 16.0).apply {
								this.textProperty().bind(
									Bindings.createObjectBinding({
										return@createObjectBinding "${
											Settings.account.value?.key?.toString()?.substring(0..20)
												?: "A very long number"
										}..."
									}, Settings.account)
								)
							}
						),
					).apply {
						this.spacing = 5.0
						this.opacity = 0.3
					}
				).apply {
					this.spacing = 20.0
				},

				FlexExpander(
					hBox = true
				),

				VBox(
					Button("Log out", Color.web("#FF6F6F"), Image(
						"/spg/client/images/settings/logout.png"
					)) {

					},
					Button("Switch account", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/switchacc.png"
					)) {

					},
					Button("Invite a friend", Color.web("#7CC0FF"), Image(
						"/spg/client/images/settings/invite.png"
					)) {

					},
					Button("Request account data", Color.web("#ECF0FF"), Image(
						"/spg/client/images/settings/accdata.png"
					)) {

					},
				).apply {
					this.spacing = 10.0
				}
			)
		}
	}
}