package gameoflife

import gameoflife.Coordinates.Absolute

sealed class Events

data class CellDied(val position: Absolute) : Events()
data class CellBorn(val position: Absolute) : Events()