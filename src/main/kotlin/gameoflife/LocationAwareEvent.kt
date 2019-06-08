package gameoflife

import gameoflife.Coordinates.Absolute

sealed class LocationAwareEvent {
    abstract val position: Absolute

    fun isInside(width: Int, height: Int): Boolean {
        return position.fitsInside(width, height)
    }
}

data class CellDied(override val position: Absolute) : LocationAwareEvent()
data class CellBorn(override val position: Absolute) : LocationAwareEvent()