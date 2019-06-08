package gameoflife

import gameoflife.ui.javafx.Board
import javafx.application.Application
import javafx.stage.Stage

class GoLApplication : Application() {
    lateinit var current: World

    override fun start(primaryStage: Stage) {
        val initialWorld = World(setOf(Coordinates.Absolute(0, 0), Coordinates.Absolute(0, 1), Coordinates.Absolute(0, 2)))
        current = initialWorld
        val board = Board(primaryStage, onEvolutionTrigger = { board:Board ->
            current = current.evolve().also { actOnUI(it, board) }.world
        })
        initializeBoard(current, board)

    }

    private fun initializeBoard(current: World, board: Board) {
        current.aliveCellsCoordinates.filter { it.fitsInside(board.width, board.height) }.map { aliveCell ->
            board.markAsAlive(aliveCell)
        }
    }

    private fun actOnUI(next: Generation, board: Board) {
        next.events.filter { it.isInside(board.width, board.height) }.forEach {
            when (it) {
                is CellBorn -> board.markAsAlive(it.position)
                is CellDied -> board.markAsDead(it.position)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(GoLApplication::class.java)
        }
    }
}

fun survivesThisGeneration(isAlive: Boolean, aliveNeightbours: Int): Boolean {
    return when (isAlive) {
        true -> shouldLive(aliveNeightbours)
        false -> shouldBecomeALive(aliveNeightbours)
    }
}

fun shouldBecomeALive(aliveNeightbours: Int): Boolean {
    return aliveNeightbours == 3
}

fun shouldLive(aliveNeightbours: Int): Boolean {
    return aliveNeightbours in 2..3
}