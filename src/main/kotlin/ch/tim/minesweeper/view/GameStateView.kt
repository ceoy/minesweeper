package ch.tim.minesweeper.view

import tornadofx.Fragment
import tornadofx.action
import tornadofx.button
import tornadofx.gridpane
import tornadofx.gridpaneConstraints
import tornadofx.paddingAll
import tornadofx.row
import tornadofx.text

class GameStateView : Fragment() {

    val gameWon: Boolean by param()
    private var shouldRestart = false

    override val root = gridpane {
        paddingAll = 50
        row {
            text {
                text = if (gameWon) "You won!" else "You lost..."
                gridpaneConstraints {
                    marginBottom = 8.0
                }
            }
        }
        row {
            button {
                text = "Try again"
                action {
                    shouldRestart = true
                    close()
                }
            }
        }
    }

    fun getResult() = shouldRestart
}