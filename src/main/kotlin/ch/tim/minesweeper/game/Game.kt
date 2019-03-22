package ch.tim.minesweeper.game

import ch.tim.minesweeper.settings.AMOUNT_OF_BOMBS
import ch.tim.minesweeper.settings.SQUARE_SIZE
import javafx.scene.layout.StackPane
import kotlin.random.Random

class Game(private val view: GamePresenter) {

    private var gameState: GameState = GameState.NO_STATE
    private val fields = mutableListOf<Field>()

    private fun addBombs(x: Int, y: Int) {
        while (fields.count { it.bomb } != AMOUNT_OF_BOMBS) {
            // add bomb
            val bombX = Random.nextInt(0, SQUARE_SIZE)
            val bombY = Random.nextInt(0, SQUARE_SIZE)
            val fieldAtPos = fields.findAt(bombX, bombY) ?: continue

            if (bombX == x && bombY == y || fieldAtPos.bomb) {
                continue
            }

            // add bomb
            fieldAtPos.bomb = true
        }
    }

    fun escape() {
        view.goToMenu()
    }

    fun reset() {
        gameState = GameState.NO_STATE
        fields.forEach {
            it.reset()
        }
    }

    fun handleShortPress(x: Int, y: Int) {
        when (gameState) {
            GameState.PLAYING -> {
                val field = fields.findAt(x, y) ?: return
                val hasSucceeded = field.uncover(fields)
                if (!hasSucceeded) {
                    // game over :(
                    gameOver()
                }
            }
            GameState.GAME_OVER -> {
            } // do nothing
            GameState.NO_STATE -> {
                startGame(x, y)
                fields.findAt(x, y)?.uncover(fields)
            }
        }
    }

    private fun startGame(x: Int, y: Int) {
        addBombs(x, y)
        gameState = GameState.PLAYING
    }

    fun handleLongPress(x: Int, y: Int) {
        // only be able to mark when playing
        if (gameState != GameState.PLAYING) return
        fields.findAt(x, y)?.markAsBomb()
    }

    fun addField(x: Int, y: Int, stackPane: StackPane) {
        fields.add(Field(x, y, pane = stackPane))
    }

    private fun MutableList<Field>.findAt(x: Int, y: Int): Field? {
        return this.find {
            it.x == x && it.y == y
        }
    }

    private fun gameOver() {
        gameState = GameState.GAME_OVER
        fields.forEach {
            it.uncoverAfterGame()
        }

        view.displayResultScreen(false)
    }

}