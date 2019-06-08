package gameoflife

import gameoflife.Coordinates.*
import org.junit.Test
import kotlin.test.assertEquals

class WorldEvolutionTests {
    @Test
    fun `Worlds evolve by death`() {
        assertEquals(World(setOf()), World(setOf(Absolute(0, 0))).evolve())
    }

    @Test
    fun `Still lifes survive forever`() {
        val block = setOf(
                Absolute(0, 0),
                Absolute(1, 0),
                Absolute(0, 1),
                Absolute(1, 1))

        assertEquals(World(block), World(block).evolve().evolve())
    }

    @Test
    fun `Life, uh, finds a way`() {
        val verticalLine = setOf(
                Absolute(0, -1),
                Absolute(0, 0),
                Absolute(0, 1))

        val horizontalLine = setOf(
                Absolute(-1, 0),
                Absolute(0, 0),
                Absolute(1, 0))

        assertEquals(World(horizontalLine), World(verticalLine).evolve())
    }
    @Test
    fun `Can find deaths`() {
        assertEquals(setOf(Absolute(1, 1)), World(setOf(Absolute(1, 1))).findDeaths(World(emptySet())))
    }

    @Test
    fun `Can find births`() {
        assertEquals(setOf(Absolute(1, 1)), World(emptySet()).findBirths(World(setOf(Absolute(1, 1)))))
    }
}