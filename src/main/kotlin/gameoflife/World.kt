package gameoflife

data class World(val aliveCellsCoordinates: Set<Coordinates.Absolute>) {
    fun aliveNeighboursOf(cell: Coordinates.Absolute): Int {
        return cell.neighbours().filter { it in aliveCellsCoordinates }.count()
    }

    infix fun notIn(other: World): Set<Coordinates.Absolute> =
            aliveCellsCoordinates.filter { it !in other.aliveCellsCoordinates }.toSet()

    fun evolve(): Pair<World,List<Events>> {
        val deaths = aliveCellsCoordinates.filter { survivesThisGeneration(true, aliveNeighboursOf(it)) }
        val births = aliveCellsCoordinates.flatMap { aliveCell ->
            aliveCell.neighbours().flatMap { possibleBirths -> possibleBirths.neighbours().filter { survivesThisGeneration(false, aliveNeighboursOf(it)) } }
        }
        return Pair(World(deaths.toSet() + births.toSet()),emptyList())
    }

    fun findBirths(next: World): Set<Coordinates.Absolute> {
        return next notIn this
    }

    fun findDeaths(next: World): Set<Coordinates.Absolute> {
        return this notIn next
    }
}