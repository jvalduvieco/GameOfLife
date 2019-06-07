/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package qq

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppTest {
    @Test fun `Any live cell with fewer than two live neighbours dies, as if caused by underpopulation`() {
        assertFalse(survivesThisGeneration(true, 1))
    }

    @Test fun `Any live cell with more than three live neighbours dies, as if by overcrowding`() {
        assertFalse(survivesThisGeneration(true,4))
    }

    @Test fun `Any live cell with two or three live neighbours lives on to the next generation`() {
        assertTrue(survivesThisGeneration(true, 2))
        assertTrue(survivesThisGeneration(true, 3))
    }

    @Test fun `Any dead cell with exactly three live neighbours becomes a live cell`() {
        assertTrue(survivesThisGeneration(false,3))
    }

    private fun survivesThisGeneration(isAlive: Boolean, aliveNeightbours: Int): Boolean {
        return when (isAlive){
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
