package model

import util.randomFreePosition
import util.randomPosition


class BouncyMap(private val width: Int, private val height: Int) : WorldMap {

    val animals = mutableMapOf<Vector2d, Animal>()

    override fun canMoveTo(position: Vector2d): Boolean {
        return position.x in 0 until width && position.y in 0 until height
    }

    override fun objectAt(position: Vector2d): Animal? {
        return animals[position]
    }

    override fun place(animal: Animal) {
        animals.entries.removeIf { it.value == animal }
        if (!canMoveTo(animal.position)) {
            throw IllegalArgumentException("Nie mozna ustawic zwierzaka poza mapą")
        }
        if (!isOccupied(animal.position)) {
            animals[animal.position] = animal
        } else {
            val randomFree = randomFreePosition(Vector2d(width, height))
            if (randomFree != null) {
                animal.position = randomFree
                animals[randomFree] = animal
            }
        }
    }


    override fun isOccupied(position: Vector2d): Boolean {
        return animals.containsKey(position)
    }
}