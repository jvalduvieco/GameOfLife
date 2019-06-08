package gameoflife

import javafx.application.Application
import javafx.collections.transformation.FilteredList
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class GoLApplication : Application() {
    val width = 5
    val height = 5
    override fun start(primaryStage: Stage?) {
        val root = StackPane()
        root.id = "root"
        val scene = Scene(root, width * 10.0, height * 10.0)
        val board = board()
        root.children.add(board)
        primaryStage?.title = "Conway's game of life"
        primaryStage?.isResizable = false
        primaryStage?.scene = scene
        primaryStage?.show()
        val initialWorld = World(setOf(Coordinates.Absolute(0, 0), Coordinates.Absolute(0, 1), Coordinates.Absolute(0, 2)))
        var current = initialWorld
        current.aliveCellsCoordinates.filter { coordinate ->
            coordinate.x in 0..width && coordinate.y in 0..height
        }.map { aliveCell ->
            markAsAlive(board, aliveCell)
        }
        scene.addEventHandler(KeyEvent.KEY_PRESSED) { key ->
            if (key.code === KeyCode.ENTER) {
                val next = current.evolve()
                actOnUI(current, next, board)
                current = next
            }
        }
    }

    private fun actOnUI(current: World, next: World, board: GridPane) {
        current.findBirths(next).filter { coordinate ->
            coordinate.x in 0..width && coordinate.y in 0..height
        }.map { birth ->
            markAsAlive(board, birth)
        }
        current.findDeaths(next).filter { coordinate ->
            coordinate.x in 0..width && coordinate.y in 0..height
        }.map { corpse ->
            markAsDead(board, corpse)
        }
    }

    private fun markAsAlive(board: GridPane, aliveCell: Coordinates.Absolute) {
        paintCell(board, aliveCell.x, aliveCell.y, Color.RED)
    }

    private fun markAsDead(board: GridPane, aliveCell: Coordinates.Absolute) {
        paintCell(board, aliveCell.x, aliveCell.y, Color.TRANSPARENT)
    }

    private fun paintCell(board: GridPane, x: Int, y: Int, color: Color) {
        val reference: FilteredList<Rectangle> = board.children.filtered { node -> GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y } as FilteredList<Rectangle>
        reference.first()?.fill = color
    }

    private fun board(): GridPane {
        val boardSpace = GridPane()
        boardSpace.isGridLinesVisible = true
        (0 until height).map { row ->
            (0 until width).map { col ->
                boardSpace.add(Rectangle(10.0, 10.0, Color.TRANSPARENT), col, row)
            }
        }
        return boardSpace
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