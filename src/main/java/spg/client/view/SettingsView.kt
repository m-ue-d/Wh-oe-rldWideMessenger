package spg.client.view

import javafx.animation.Interpolator
import javafx.beans.binding.Bindings
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.control.TextField
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.util.Duration
import javafx.util.StringConverter
import spg.client.model.Settings
import spg.client.view.utility.AnyTransition
import spg.client.view.utility.ColorUtil
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FontManager

class SettingsView : BorderPane() {
	init {
		this.padding = Insets(10.0)
		this.center = ScrollPane(
			SettingsPane()
		).apply {
			this.isFitToWidth = true
			this.background = Background.fill(Color.TRANSPARENT)
		}
		this.backgroundProperty().bind(
			Bindings.createObjectBinding({
				Background(
					BackgroundFill(
						Settings.bgSecondary.value,
						CornerRadii(10.0),
						Insets.EMPTY
					)
				)
			}, Settings.bgSecondary)
		)
	}

	class SettingsPane : VBox(
		SettingsGroup("Appearance"),
		SettingsItem(
			"Primary Color",
			"The primary color of the application. It is the darkest color of all.",
			SettingsItem.ColorField(Settings.bgPrimary)
		),
		SettingsItem(
			"Secondary Color",
			"A slightly lighter variant of the primary color. Used for elevated elements.",
			SettingsItem.ColorField(Settings.bgSecondary)
		),
		SettingsItem(
			"Tertiary Color",
			"The lightest color in the application. Used for backgrounds and light fonts.",
			SettingsItem.ColorField(Settings.bgTertiary)
		),
		SettingsItem(
			"Accent Color",
			"A colorful alternative to the other colors. Mainly used for tab indicators.",
			SettingsItem.ColorField(Settings.colorAccent)
		),
		SettingsItem(
			"Font Color",
			"The main color of the application's font. Usually set to a color close to white.",
			SettingsItem.ColorField(Settings.fontMain)
		),
		SettingsGroup("Account"),
	) {
		init {
			this.spacing = 10.0
		}
	}

	class SettingsGroup(private val title: String) : HBox() {
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

	class SettingsItem(private val title: String, private val description: String, private val setting: Region) : HBox() {
		private val hoverOpacity = SimpleDoubleProperty(0.0)
		init {
			this.alignment = Pos.CENTER
			this.padding = Insets(10.0, 20.0, 10.0, 20.0)
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

		class ColorField(private val property: ObjectProperty<Color>) : TextField() {
			init {
				this.alignment = Pos.CENTER_LEFT
				this.deselect()
				this.font = FontManager.boldFont(16.0)
				this.styleProperty().bind(
					Bindings.createObjectBinding({
						return@createObjectBinding "-fx-text-fill: ${
							ColorUtil.toHex(
								Settings.bgPrimary.value.invert()
							)
						};"
					}, property)
				)
				Bindings.bindBidirectional(
					this.textProperty(),
					property,
					object : StringConverter<Color>() {
						override fun toString(obj : Color) : String {
							return ColorUtil.toHex(obj)
						}

						override fun fromString(str : String) : Color {
							return ColorUtil.toColor(str)
						}
					}
				)
			}
		}
	}
}