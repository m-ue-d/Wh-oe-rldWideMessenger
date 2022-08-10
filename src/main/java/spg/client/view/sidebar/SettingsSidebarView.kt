package spg.client.view.sidebar

import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.ScrollPane
import javafx.scene.layout.*
import javafx.util.Callback
import spg.client.model.Internal
import spg.client.model.Settings
import spg.client.view.SettingsView
import spg.client.view.utility.FontManager
import spg.client.view.utility.HoverTransition

object SettingsSidebarView : VBox() {
	init {
		this.spacing = 10.0
		this.children.addAll(
			ScrollPane(
				ListView<String>().apply {
					this.items = Internal.settingGroups
					this.cellFactory = Callback {
						SettingGroupCell()
					}
					this.selectionModel.selectedItemProperty().addListener { _, _, v ->
						if (v != null) {
							SettingsView.scrollToGroup(v)
						}
					}
				},
			).apply {
				setVgrow(this, Priority.ALWAYS)
				this.isFitToWidth = true
				this.background = Background.EMPTY
			}
		)
	}

	class SettingGroupCell : ListCell<String>() {
		override fun updateItem(item : String?, empty : Boolean) {
			super.updateItem(item, empty)
			if (item != null) {
				graphic = HBox(
					FontManager.boldLabel(
						item
					)
				).apply {
					this.alignment = Pos.CENTER_LEFT
					this.spacing = 10.0
					this.padding = Insets(15.0)
					this.backgroundProperty().bind(
						Bindings.createObjectBinding({
							Background(
								BackgroundFill(
									Settings.bgPrimary.value,
									CornerRadii(10.0),
									Insets.EMPTY
								)
							)
						}, Settings.bgPrimary)
					)

					this.onMouseEntered = EventHandler {
						HoverTransition.onMouseEntered(this@SettingGroupCell)
					}

					this.onMouseExited = EventHandler {
						HoverTransition.onMouseExited(this@SettingGroupCell)
					}
				}
			}
		}
	}
}