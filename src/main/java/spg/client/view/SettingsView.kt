package spg.client.view

import AccountArea
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.util.Duration
import spg.client.control.network.ClientNetwork
import spg.client.model.Internal
import spg.client.model.Settings
import spg.client.view.settings.ColorArea
import spg.client.view.settings.NetworkArea
import spg.client.view.template.Button
import spg.client.view.template.ColorField
import spg.client.view.template.ViewPane
import spg.client.view.utility.*
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.math.min

object SettingsView : ViewPane() {

	private val groups: MutableMap<String, Node> = mutableMapOf()
	private val scrollPane: ScrollPane

	init {
		this.center = ScrollPane(
			SettingsPane
		).apply {
			scrollPane = this
			this.maxWidth = 1300.0
			this.isFitToWidth = true
			this.background = Background.fill(Color.TRANSPARENT)
		}
	}

	fun scrollToGroup(group : String) {
		AnyTransition {
			scrollPane.vvalue = it
		}.apply {
			this.duration = Duration.seconds(0.5)
			this.interpolator = Interpolator.easeOut
			this.from = scrollPane.vvalue
			this.to = min( // Scroll element into view
				groups[group]!!.boundsInParent.minY.div(
					scrollPane.content.layoutBounds.height - scrollPane.height
				), 1.0
			)
		}.play()
	}

	object SettingsPane : VBox(
		SettingsGroup(Internal.settingGroups[0]), // use indexes because of synchronization with listview
		AccountArea,

		SettingsGroup(Internal.settingGroups[1]),
		ColorArea,

		SettingsGroup(Internal.settingGroups[2]),
		NetworkArea
	) {
		init {
			this.spacing = 10.0
		}
	}

	class SettingsGroup(title: String) : HBox() {
		init {
			groups[title] = this
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
					this.interpolator = Interpolator.easeOut
				}.play()
			}

			this.onMouseExited = EventHandler {
				AnyTransition {
					hoverOpacity.set(it)
				}.apply {
					this.from = hoverOpacity.value
					this.to = 0.0
					this.duration = Duration.seconds(0.3)
					this.interpolator = Interpolator.easeOut
				}.play()
			}
		}
	}
}