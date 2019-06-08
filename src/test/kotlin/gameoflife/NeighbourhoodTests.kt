package gameoflife

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NeighbourhoodTests {
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
}