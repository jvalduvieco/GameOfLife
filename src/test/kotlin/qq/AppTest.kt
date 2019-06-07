/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package qq

import qq.AppTest.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppTest {
    @Test
    fun `Any live cell with fewer than two live neighbours dies, as if caused by underpopulation`() {
        assertFalse(survivesThisGeneration(true, 1))
    }

    @Test
    fun `Any live cell with more than three live neighbours dies, as if by overcrowding`() {
        assertFalse(survivesThisGeneration(true, 4))
    }

    @Test
    fun `Any live cell with two or three live neighbours lives on to the next generation`() {
        assertTrue(survivesThisGeneration(true, 2))
        assertTrue(survivesThisGeneration(true, 3))
    }

    @Test
    fun `Any dead cell with exactly three live neighbours becomes a live cell`() {
        assertTrue(survivesThisGeneration(false, 3))
    }

    @Test
    fun `Can calculate next gen`() {
        assertEqual(listOf(
                listOf(false, false, false),
                listOf(false, false, false),
                listOf(false, false, false)),
                nextGeneration(listOf(
                        listOf(false, false, false),
                        listOf(false, true, false),
                        listOf(false, false, false))))
    }

    private fun nextGeneration(rows: List<List<Boolean>>): List<List<Boolean>> {
        return rows.aliveCells().nextGeneration().toRows()
    }

    @Test
    fun `Can find neighbours of a central cell`() {
        assertEquals(setOf(
                Coordinates.Absolute(9, 9), Coordinates.Absolute(10, 9), Coordinates.Absolute(11, 9),
                Coordinates.Absolute(9, 10), Coordinates.Absolute(11, 10),
                Coordinates.Absolute(9, 11), Coordinates.Absolute(10, 11), Coordinates.Absolute(11, 11)),
                Coordinates.Absolute(10, 10).neighbours())
    }

    @Test
    fun `Cells live in a world`() {
        assertNotNull(World(setOf(Coordinates.Absolute(0, 0))))
    }

    @Test
    fun `Can find the number of alive cells in the neighbourhood`() {
        assertEquals(1, World(setOf(Coordinates.Absolute(0, 0))).aliveNeighboursOf(Coordinates.Absolute(1, 1)))
    }

    @Test
    fun `Worlds evolve by death`() {
        assertEquals(World(setOf()), World(setOf(Coordinates.Absolute(0, 0))).evolve())
    }

    @Test
    fun `Still lifes survive forever`() {
        val block = setOf(
                Coordinates.Absolute(0, 0),
                Coordinates.Absolute(1, 0),
                Coordinates.Absolute(0, 1),
                Coordinates.Absolute(1, 1))

        assertEquals(World(block), World(block).evolve().evolve())
    }

    @Test
    fun `Life, uh, finds a way`() {
        val verticalLine = setOf(
                Coordinates.Absolute(0, -1),
                Coordinates.Absolute(0, 0),
                Coordinates.Absolute(0, 1))

        val horizontalLine = setOf(
                Coordinates.Absolute(-1, 0),
                Coordinates.Absolute(0, 0),
                Coordinates.Absolute(1, 0))

        assertEquals(World(horizontalLine), World(verticalLine).evolve())
    }

    @Test
    fun `Can find deaths`() {
        assertEquals(setOf(Coordinates.Absolute(1,1)), findDeaths(World(setOf(Coordinates.Absolute(1,1))), World(emptySet())))
    }

    @Test
    fun `Can find births`() {
        assertEquals(setOf(Coordinates.Absolute(1,1)), findBirths(World(emptySet()), World(setOf(Coordinates.Absolute(1,1)))))
    }

    private fun findBirths(previous: World, current: World): Set<Coordinates.Absolute> {
        return current notIn previous
    }

    private fun findDeaths(previous: World, current: World): Set<Coordinates.Absolute> {
        return previous notIn current
    }

    data class World(val aliveCellsCoordinates: Set<Coordinates.Absolute>) {
        fun aliveNeighboursOf(cell: Coordinates.Absolute): Int {
            return cell.neighbours().filter { it in aliveCellsCoordinates }.count()
        }

        infix fun notIn(other: World): Set<Coordinates.Absolute> =
                aliveCellsCoordinates.filter { it !in other.aliveCellsCoordinates }.toSet()

        fun evolve(): World {
            val deaths = aliveCellsCoordinates.filter { survivesThisGeneration(true, aliveNeighboursOf(it)) }
            val births = aliveCellsCoordinates.flatMap {
                aliveCell -> aliveCell.neighbours().flatMap {
                    possibleBirths -> possibleBirths.neighbours().filter { survivesThisGeneration(false, aliveNeighboursOf(it)) } } }
            return World(deaths.toSet() + births.toSet())
        }
    }

    class Coordinates {
        data class Absolute(val x: Int, val y: Int) {

            private fun translate(other: Relative): Absolute {
                return Absolute(x + other.x, y + other.y)
            }

            fun neighbours(): Set<Absolute> {
                return setOf(
                        Relative(-1, -1), Relative(0, -1), Relative(1, -1),
                        Relative(-1, 0), Relative(1, 0),
                        Relative(-1, 1), Relative(0, 1), Relative(1, 1)
                ).map { translate(it) }.toSet()
            }
        }

        data class Relative(val x: Int, val y: Int)
    }

    @Test
    fun `Can create a view`(){
        assertNotNull(View(320,320))

    }
    class View(maxX: Int, maxY: Int) {

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

private fun List<Coordinates.Absolute>.nextGeneration(): List<Coordinates.Absolute> {
    return this.map { it.neighbours() }
}

private fun List<Coordinates>.toRows(): List<List<Boolean>> {
    TODO("not implemented")
}

private fun List<List<Boolean>>.aliveCells(): List<Coordinates.Absolute> {
    return mapIndexed { rowNumber, columns ->
        columns.mapIndexed { columnNumber, isAlive ->
            if (isAlive) Coordinates.Absolute(rowNumber, columnNumber) else null
        }
    }.flatten().filterNotNull()
}
