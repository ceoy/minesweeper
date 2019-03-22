package ch.tim.minesweeper.game

import ch.tim.minesweeper.app.SpriteViewports
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane

data class Field(val x: Int,
                 val y: Int,
                 var uncovered: Boolean = false,
                 private val pane: StackPane,
                 var bomb: Boolean = false,
                 private var markedAsBomb: Boolean = false) {


    /**
     * Tries to uncover the field, returns true if it was successful, false if it was a bomb all along
     */
    fun uncover(fields: List<Field>): Boolean {

        if (uncovered) {
            return true
        }

        uncovered = true // this needs to be set before we go uncovering more, otherwise we get a stackoverflow

        if (bomb) {
            pane.getImage {
                it.viewport = SpriteViewports.MINE_LOSE
            }
            return false
        }

        // find all neighbours
        val neighbours = fields.filter {
            (it.y == y - 1 && it.x == x) || // top
                    (it.y == y - 1 && it.x == x - 1) || // left top
                    (it.y == y - 1 && it.x == x + 1) || // right top
                    (it.y == y && it.x == x + 1) || // right
                    (it.y == y && it.x == x - 1) || // left
                    (it.y == y + 1 && it.x == x) || // bottom
                    (it.y == y + 1 && it.x == x - 1) || // left bottom
                    (it.y == y + 1 && it.x == x + 1) // right bottom
        }

        val amountOfBombs = neighbours.count { it.bomb }

        if (amountOfBombs == 0) {
            neighbours.forEach { it.uncover(fields) }
        }

        pane.getImage {
            it.viewport = when (amountOfBombs) {
                1 -> SpriteViewports.CELL_1
                2 -> SpriteViewports.CELL_2
                3 -> SpriteViewports.CELL_3
                4 -> SpriteViewports.CELL_4
                5 -> SpriteViewports.CELL_5
                6 -> SpriteViewports.CELL_6
                7 -> SpriteViewports.CELL_7
                8 -> SpriteViewports.CELL_8
                else -> SpriteViewports.CELL_PRESSED
            }
        }

        return true
    }

    fun uncoverAfterGame() {
        // skip already uncovered fields
        if (uncovered || !bomb) return

        pane.getImage {
            it.viewport = SpriteViewports.MINE
        }

    }

    fun reset() {
        bomb = false
        uncovered = false
        pane.getImage {
            it.viewport = SpriteViewports.CELL_INIT
        }
    }

    fun markAsBomb() {
        // skip already uncovered cells
        if (uncovered) return

        pane.getImage {
            it.viewport = if (!markedAsBomb) SpriteViewports.CELL_FLAG else SpriteViewports.CELL_INIT
        }

        markedAsBomb = !markedAsBomb
    }

    private fun StackPane.getImage(block: (ImageView) -> Unit) {
        (children[0] as? ImageView)?.let {
            block(it)
        }
    }
}