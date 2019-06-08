package gameoflife

class Coordinates {
    data class Absolute(val x: Int, val y: Int) {

        private fun translate(other: Relative): Absolute {
            return Absolute(x + other.x, y + other.y)
        }

        fun neighbours(): Set<Absolute> {
            return setOf(
                    Relative(-1, -1), Relative(0, -1), Relative(1, -1),
                    Relative(-1, 0), Relative(1, 0),
                    Relative(-1, 1), Relative(0, 1), Relative(1, 1)
            ).map { translate(it) }.toSet()
        }

        fun fitsInside(width: Int, height: Int): Boolean {
            return x in  0 .. width && y in 0 .. height
        }
    }

    data class Relative(val x: Int, val y: Int)
}