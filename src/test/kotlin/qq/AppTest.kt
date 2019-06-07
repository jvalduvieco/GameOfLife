/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package qq

import com.sun.xml.internal.bind.v2.runtime.Coordinator
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
    fun `Can find neighbours of a central cell`() {
        assertEquals(listOf(
                "NW", "N", "NE",
                "W", "E",
                "SW", "S", "SE"),
                neightbourOf(10, 10))
    }
    @Test
    fun `Can translate Direction names to relative positions`() {
        assertEquals(listOf(
                "NW", "N", "NE",
                "W", "E",
                "SW", "S", "SE").map { toRelativePosition(it) },
                listOf(
                        Coord(-1,-1), Coord(0,-1),  Coord(1,-1),
                        Coord(-1,0),                       Coord(1,0),
                        Coord(-1, 1), Coord(0,1),   Coord(1,1)))
    }

    private fun toRelativePosition(directionName: String): Coord {
        return when(directionName){
            "NW"->Coord(-1,-1)
            "N"-> Coord(0,-1)
            "NE"-> Coord(1,-1)
            "W"->Coord(-1,0)
            "E"->Coord(1,0)
            "SW"->Coord(-1, 1)
            "S"->Coord(0,1)
            "SE"->Coord(1,1)
            else -> throw Exception("Huh?!")
        }
    }

    data class Coord (val x: Int, val y:Int)

    private fun neightbourOf(x: Int, y: Int): List<String> {
        return listOf("NW", "N", "NE",
                "W", "E",
                "SW", "S", "SE")
    }

    private fun survivesThisGeneration(isAlive: Boolean, aliveNeightbours: Int): Boolean {
        return when (isAlive) {
            true -> shouldLive(aliveNeightbours)
            false -> shouldBecomeALive(aliveNeightbours)
        }
    }

    private fun shouldBecomeALive(aliveNeightbours: Int): Boolean {
        return aliveNeightbours == 3
    }

    private fun shouldLive(aliveNeightbours: Int): Boolean {
        return aliveNeightbours in 2..3
    }
}
