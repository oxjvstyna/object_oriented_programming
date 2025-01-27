import model.Animal
import model.BouncyMap
import model.Vector2d
import util.MapDirection

fun main() {
    val map = BouncyMap(10, 10);
    map.place(Animal(Vector2d(5, 6), MapDirection.NORTH))
    for (i in 0 until 10) {
        for (j in 0 until 10) {
            if (map.objectAt(Vector2d(i, j)) != null) {
                print("X")
            } else {
                print(" ")
            }
        }
        println()
    }
}
