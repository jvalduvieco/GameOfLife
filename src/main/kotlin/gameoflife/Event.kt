package gameoflife

import gameoflife.Coordinates.Absolute

sealed class Event

data class CellDied(val position: Absolute) : Event()
data class CellBorn(val position: Absolute) : Event()