package ch.tim.minesweeper.app

import ch.tim.minesweeper.view.GameView
import javafx.stage.Stage
import tornadofx.App

class MineSweeperApp: App(GameView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}