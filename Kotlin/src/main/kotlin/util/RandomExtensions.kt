package util

import model.Vector2d
import model.WorldMap

fun WorldMap.randomFreePosition(mapSize: Vector2d): Vector2d?{
    val emptyPos: ArrayList<Vector2d> = ArrayList()
    for (i in 0 until (mapSize.x)){
        for (j in 0 until (mapSize.y))
        {
            if (!this.isOccupied(Vector2d(i,j))) {
                emptyPos.add(Vector2d(i, j))
            }
        }
    }
    return emptyPos.randomOrNull()
}

fun randomPosition(mapSize: Vector2d): Vector2d {
    val emptyPos: ArrayList<Vector2d> = ArrayList()
    for (i in 0 until (mapSize.x)){
        for (j in 0 until (mapSize.y)){
            emptyPos.add(Vector2d(i,j))
        }
    }
    return emptyPos.random()
}
