package `Zajecia 1`


fun sum(x: Int, y: Int) = x + y

var zmienna: Int = 0 // zmienna
val stala: Int = 1 // stala

var nu: String? = "hi"
var nulla: String = "hi"


class Student(val imie: String)

val s1 = Student("a")

open class Piesel {
    open fun hau() {
        println("hauhauhau")
    }
}

class Labladador: Piesel() {
    override fun hau() {
        println("lablahau")
    }
}

fun noIf(student: Any) {
    when(student) {
        12345 -> print("Indeks")
        "Jan" -> print("po imieniu")
        is Double -> print("wzrost")
        else -> print("nie znaju")
    }
}

fun loops(args: Array<String>) {
    var iterator = 0

    for(arg in args) {
        println(arg)
    }

    var range = 0..5 step 2

    var range2 = 5 downTo 0 step 2

    var range3 = 0 until 5

    var range4 = 'a' to 'g'

    for (x in range) {
        print(x)
    }

}

var studenci = setOf("Kowalski", "Stepien", "Goliat")

var studenci2 = setOf("Goliat", "Stepien", "Kowalski")

fun cos() {
    println(studenci == studenci2)
    println(studenci === studenci2) // sprawdza typ
}

fun max(x: Int, y: Int) = if (x>y) x else y

// singleton
object Czlowiek {
    fun pij() {
        println("gulgulgulgul")
    }
}

fun main(args: Array<String>) {
    println("Hello World!")
    nu = null
    cos()

    Czlowiek.pij()
}