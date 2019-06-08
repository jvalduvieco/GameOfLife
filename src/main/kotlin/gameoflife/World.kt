package gameoflife

data class World(val aliveCellsCoordinates: Set<Coordinates.Absolute>) {
    fun aliveNeighboursOf(cell: Coordinates.Absolute): Int {
        return cell.neighbours().filter { it in aliveCellsCoordinates }.count()
    }

    infix fun notIn(other: World): Set<Coordinates.Absolute> =
            aliveCellsCoordinates.filter { it !in other.aliveCellsCoordinates }.toSet()

    fun evolve(): Generation {
        val deaths = aliveCellsCoordinates.filter { survivesThisGeneration(true, aliveNeighboursOf(it)) }
        val births = aliveCellsCoordinates.flatMap { aliveCell ->
            aliveCell.neighbours().flatMap { possibleBirths -> possibleBirths.neighbours().filter { survivesThisGeneration(false, aliveNeighboursOf(it)) } }
        }
        val next = World(deaths.toSet() + births.toSet())
        val events = findBirths(next).map { CellBorn(it) } + findDeaths(next).map { CellDied(it) }
        return Generation(next, events.toSet())
    }

    fun findBirths(next: World): Set<Coordinates.Absolute> {
        return next notIn this
    }

    fun findDeaths(next: World): Set<Coordinates.Absolute> {
        return this notIn next
    }
}

data class Generation(val world: World, val events: Collection<Event>)