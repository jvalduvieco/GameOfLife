package gameoflife

import gameoflife.Coordinates.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NeighbourhoodTests {
    @Test
    fun `Can find neighbours of a central cell`() {
        assertEquals(setOf(
                Absolute(9, 9), Absolute(10, 9), Absolute(11, 9),
                Absolute(9, 10), Absolute(11, 10),
                Absolute(9, 11), Absolute(10, 11), Absolute(11, 11)),
                Absolute(10, 10).neighbours())
    }

    @Test
    fun `Cells live in a world`() {
        assertNotNull(World(setOf(Absolute(0, 0))))
    }
    @Test
    fun `Can find the number of alive cells in the neighbourhood`() {
        assertEquals(1,
                World(setOf(Absolute(0, 0))).aliveNeighboursOf(Absolute(1, 1)))
    }
}