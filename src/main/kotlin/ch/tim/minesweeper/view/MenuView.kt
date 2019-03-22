package ch.tim.minesweeper.view

import javafx.scene.Parent
import javafx.scene.text.TextAlignment
import tornadofx.Fragment
import tornadofx.action
import tornadofx.button
import tornadofx.gridpane
import tornadofx.gridpaneConstraints
import tornadofx.paddingAll
import tornadofx.row
import tornadofx.text
import tornadofx.useMaxWidth


class MenuView : Fragment() {
    override val root: Parent = gridpane {
        paddingAll = 50
        row {
            text {
                text = "Paused"
                gridpaneConstraints {
                    marginBottom = 8.0
                }
            }
        }
        row {
            button {
                text = "Resume"
                useMaxWidth = true
                gridpaneConstraints {
                    marginBottom = 8.0
                }
                action {
                    close()
                }
            }
        }

        row {
            button {
                useMaxWidth = true
                text = "Exit"
                action {
                    primaryStage.close()
                }
            }
        }
    }
}