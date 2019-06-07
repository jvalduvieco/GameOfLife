/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package qq

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppTest {
    @Test fun `Any live cell with fewer than two live neighbours dies`() {
        assertEquals(NextGeneration.Survives, nextGeneration(true, 1))
    }

    @Test fun `Any live cell with more than one live neighbours lives`() {
        assertTrue(nextGeneration(true, 2))
    }

    @Test fun `Any live cell with three goes into next generation`() {
        assertTrue(nextGeneration(true, 3))
    }

    @Test fun `Any live cell with more than three alive neighbours dies`() {
        assertFalse(nextGeneration(true,4))
    }

    @Test fun `Any dead cell with more than three a live neighbours becomes alive`() {
        assertTrue(nextGeneration(false,3))
    }

    private fun nextGeneration(isAlive: Boolean, aliveNeightbours: Int): Boolean {
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
