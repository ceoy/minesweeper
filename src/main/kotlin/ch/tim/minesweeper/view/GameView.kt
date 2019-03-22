package ch.tim.minesweeper.view

import ch.tim.minesweeper.app.SpriteViewports
import ch.tim.minesweeper.game.Game
import ch.tim.minesweeper.game.GamePresenter
import ch.tim.minesweeper.settings.CELL_SIZE
import ch.tim.minesweeper.settings.SQUARE_SIZE
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.stage.StageStyle
import tornadofx.*

class GameView : View("Minesweeper"), GamePresenter {

    private val game = Game(this)

    override val root = gridpane {

        setOnKeyPressed {
            when (it.code) {
                KeyCode.ESCAPE -> game.escape()
                KeyCode.R -> game.reset()
                else -> {
                    // dont care
                }
            }
        }

        // load image only once
        val singleImage = Image("sprite.png")

        (0..SQUARE_SIZE).forEach { y ->
            row {
                (0..SQUARE_SIZE).forEach { x ->
                    stackpane {
                        imageview {
                            image = singleImage
                            val vp = SpriteViewports.CELL_INIT
                            viewport = vp


                            scaleX = CELL_SIZE / vp.width
                            scaleY = CELL_SIZE / vp.height
                        }

                        prefHeight = CELL_SIZE
                        prefWidth = CELL_SIZE

                        shortpress {
                            game.handleShortPress(x, y)
                        }

                        longpress {
                            game.handleLongPress(x, y)
                        }

                        onHover {
                            requestFocus() // so that the field then handles the keyPress
                        }

                        setOnKeyPressed {
                            if (it.code == KeyCode.SPACE) {
                                game.handleLongPress(x, y)
                            }
                        }

                        game.addField(x, y, this)
                    }
                }
            }
        }
    }

    override fun closeView() {
        this.close()
    }

    override fun goToMenu() {
        find(MenuView::class).openModal(stageStyle = StageStyle.UNDECORATED)
    }

    override fun displayResultScreen(gameWon: Boolean) {
        val modal = find(GameStateView::class, mapOf(GameStateView::gameWon to gameWon)).apply {
            openModal(stageStyle = StageStyle.UNDECORATED, block = true)
        }

        val result = modal.getResult()
        if (result) {
            game.reset()
        }

    }
}