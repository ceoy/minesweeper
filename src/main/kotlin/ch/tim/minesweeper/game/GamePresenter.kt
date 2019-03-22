package ch.tim.minesweeper.game

interface GamePresenter {
    /**
     * Closes the View
     */
    fun closeView()

    /**
     * Go to the Menu View
     */
    fun goToMenu()

    /**
     * Displays a Result Screen
     */
    fun displayResultScreen(gameWon: Boolean)
}