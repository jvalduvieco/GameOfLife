package gameoflife

import org.junit.Test
import kotlin.test.assertEquals

class WorldEvolutionTests {
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
        assertEquals(setOf(Coordinates.Absolute(1, 1)), World(setOf(Coordinates.Absolute(1, 1))).findDeaths(World(emptySet())))
    }

    @Test
    fun `Can find births`() {
        assertEquals(setOf(Coordinates.Absolute(1, 1)), World(emptySet()).findBirths(World(setOf(Coordinates.Absolute(1, 1)))))
    }
}