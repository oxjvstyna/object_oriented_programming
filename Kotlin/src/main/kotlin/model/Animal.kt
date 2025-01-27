package model

import util.MapDirection
import util.MoveDirection


data class Animal (
    var position: Vector2d,
    var orientation: MapDirection = MapDirection.NORTH,
) {


    override fun toString(): String = when (orientation) {
        MapDirection.EAST -> ">"
        MapDirection.NORTH -> "^"
        MapDirection.SOUTH -> "v"
        MapDirection.WEST -> "<"
    }

    fun isAt(position: Vector2d): Boolean = this.position == position

    fun move(direction: MoveDirection, validator: WorldMap) {
        orientation = when (direction) {
            MoveDirection.RIGHT -> orientation.next()
            MoveDirection.LEFT -> orientation.previous()
            MoveDirection.FORWARD -> {
                val nextPosition = position + orientation.toUnitVector();
                if (validator.canMoveTo(nextPosition)) position = nextPosition
                orientation
            }
            MoveDirection.BACKWARD -> {
                val nextPosition = position - orientation.toUnitVector();
                if (validator.canMoveTo(nextPosition)) position = nextPosition
                orientation
            }
        }
    }
}
