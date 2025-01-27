package util

enum class MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    override fun toString(): String {
        return when (this) {
            EAST -> ">"
            WEST -> "<"
            NORTH -> "^"
            SOUTH -> "v"
        }
    }

    operator fun next(): MapDirection {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun previous(): MapDirection {
        return when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }
}