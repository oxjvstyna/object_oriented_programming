package model

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import util.randomFreePosition
import util.randomPosition

class BouncyMapTest : FunSpec({

    val width = 5
    val height = 5
    val map = BouncyMap(width, height)

    test("canMoveTo") {
        map.canMoveTo(Vector2d(0, 0)) shouldBe true
        map.canMoveTo(Vector2d(width - 1, height - 1)) shouldBe true
        map.canMoveTo(Vector2d(width, height)) shouldBe false
        map.canMoveTo(Vector2d(-1, -1)) shouldBe false
    }

    test("objectAt") {
        val animal = Animal(Vector2d(2, 2))
        map.place(animal)

        map.objectAt(Vector2d(2, 2)) shouldBe animal
        map.objectAt(Vector2d(0, 0)) shouldBe null
    }

    test("place without collision") {
        val animal = Animal(Vector2d(1, 1))
        map.place(animal)

        map.objectAt(Vector2d(1, 1)) shouldBe animal
    }

    test("place with collision and free position") {
        val animal1 = Animal(Vector2d(1, 1))
        val animal2 = Animal(Vector2d(1, 1))

        map.place(animal1)
        map.place(animal2)

        map.objectAt(Vector2d(1, 1)) shouldBe animal1
        val newPosition = map.animals.entries.find { it.value == animal2 }?.key
        newPosition shouldNotBe null
        map.objectAt(newPosition!!) shouldBe animal2}

    test("place with collision and no free position") {
        val allPositions = (0 until width).flatMap { x -> (0 until height).map { y -> Vector2d(x, y) } }
        val animals = allPositions.map { Animal(it) }

        // Zapełnij mapę
        animals.forEach { map.place(it) }

        // Dodaj nowe zwierzę
        val newAnimal = Animal(Vector2d(2, 2))
        map.place(newAnimal)

        // Sprawdź, czy nowe zwierzę wyparło inne
        map.objectAt(Vector2d(2, 2)) shouldBe newAnimal
        map.animals.size shouldBe width * height
    }

    test("isOccupied") {
        val animal = Animal(Vector2d(3, 3))
        map.place(animal)

        println(animal.position)

        map.isOccupied(Vector2d(3, 3)) shouldBe true
    }
})
