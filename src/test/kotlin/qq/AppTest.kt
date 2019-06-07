/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package qq

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
                AbsoluteCoordinates(9, 9), AbsoluteCoordinates(10, 9), AbsoluteCoordinates(11, 9),
                AbsoluteCoordinates(9, 10), AbsoluteCoordinates(11, 10),
                AbsoluteCoordinates(9, 11), AbsoluteCoordinates(10, 11), AbsoluteCoordinates(11, 11)),
                AbsoluteCoordinates(10, 10).neighbours())
    }

    data class AbsoluteCoordinates(val x: Int, val y: Int) {

        private fun translate(other: RelativeCoordinates): AbsoluteCoordinates {
            return AbsoluteCoordinates(x + other.x, y + other.y)
        }

        fun neighbours(): List<AbsoluteCoordinates> {
            return listOf(
                    RelativeCoordinates(-1, -1), RelativeCoordinates(0, -1), RelativeCoordinates(1, -1),
                    RelativeCoordinates(-1, 0), RelativeCoordinates(1, 0),
                    RelativeCoordinates(-1, 1), RelativeCoordinates(0, 1), RelativeCoordinates(1, 1)
            ).map { translate(it) }
        }
    }

    data class RelativeCoordinates(val x:Int, val y:Int)

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
