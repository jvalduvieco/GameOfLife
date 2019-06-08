package gameoflife.ui.javafx

import gameoflife.Coordinates
import javafx.collections.transformation.FilteredList
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.Stage

class Board(primaryStage: Stage, val onEvolutionTrigger: (Board) -> Unit) {
    val width = 5
    val height = 5
    val board= board()

    init {
        val root = StackPane()
        root.id = "root"
        val scene = Scene(root, width * 10.0, height * 10.0)
        root.children.add(board)
        primaryStage.title = "Conway's game of life"
        primaryStage.isResizable = false
        primaryStage.scene = scene
        primaryStage.show()
        scene.addEventHandler(KeyEvent.KEY_PRESSED) { key ->
            if (key.code === KeyCode.ENTER) {
                onEvolutionTrigger(this)
            }
        }
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

    fun markAsAlive(aliveCell: Coordinates.Absolute) {
        paintCell(board, aliveCell.x, aliveCell.y, Color.RED)
    }

    fun markAsDead(aliveCell: Coordinates.Absolute) {
        paintCell(board, aliveCell.x, aliveCell.y, Color.TRANSPARENT)
    }

    private fun paintCell(board: GridPane, x: Int, y: Int, color: Color) {
        val reference: FilteredList<Rectangle> = board.children.filtered { node -> GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y } as FilteredList<Rectangle>
        reference.first()?.fill = color
    }
}