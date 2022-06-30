package spg.client.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import spg.client.model.Current
import spg.client.view.utility.FlexExpander
import spg.client.view.utility.FontManager

class MenubarView : HBox() {
	init {
		this.padding = Insets(10.0, 20.0, 10.0, 20.0)
		this.spacing = 40.0
		this.prefHeight = 60.0
		this.alignment = Pos.CENTER_RIGHT
		this.children.addAll(
			HBox(
				FontManager.boldLabel("", 24.0).apply {
					this.textProperty().bind(Current.panel)
				},
				// search bar
			).apply {
				this.alignment = Pos.CENTER
				this.spacing = 10.0
			},

			FlexExpander(
				hBox = true
			),

			HBox(
				Circle(15.0).apply {
					BorderPane.setAlignment(this, Pos.TOP_CENTER)
					this.fill = ImagePattern(
						Image("spg/server/database/avatars/0.png")
					)
				},
				FontManager.regularLabel("Heinz"),
				BorderPane(
					ImageView(
						Image("spg/client/images/misc/expand.png")
					).apply {
						this.opacity = 0.5
						this.fitWidth = 20.0
						this.fitHeight = 20.0
					},
				)
			).apply {
				this.alignment = Pos.CENTER
				this.spacing = 10.0
			}
		)
	}
}