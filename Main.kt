import kotlin.math.max
import kotlin.random.Random

data class Obstacle(val position: Int, val displacement: Int)

val players = arrayOf("Dan", "Innes")
val boardLength = 50
val snakes = arrayOf(
    Obstacle(5, 4),
    Obstacle(16, 6),
    Obstacle(25, 5),
    Obstacle(33, 15),
    Obstacle(40, 31),
    Obstacle(49, 11)
)
val ladders = arrayOf(
    Obstacle(2, 16),
    Obstacle(11, 11),
    Obstacle(19, 5),
    Obstacle(26, 20),
    Obstacle(35, 6),
    Obstacle(42, 4)
)
val positions = mutableMapOf<String, Int>()


fun main() {
    if (players.size == 1) {
        println("We need more than one player to play snakes and ladders!")
    }
    players.forEach { positions[it] = 0 }

    var activeIndex = Random.nextInt(players.size)
    println(players[activeIndex] + " goes first!")

    val maxIterations = 1000
    var currentIteration = 0
    while (currentIteration < maxIterations) {
        var currentPosition = positions[players[activeIndex]]!!
        println(players[activeIndex] + " takes their turn. Currently on square " + currentPosition)
        val diceRoll = Random.nextInt(6) + 1
        println(players[activeIndex] + " has rolled a " + diceRoll + "!")
        currentPosition = (currentPosition + diceRoll).coerceAtMost(boardLength)
        if (currentPosition >= boardLength) {
            println(players[activeIndex] + " has reached the final position! They win!")
            println("Hope you had a fun game")
            return
        }
        println(players[activeIndex] + " moves to square " + currentPosition)
        snakes.firstOrNull { snake -> snake.position == currentPosition }?.let {
            currentPosition -= it.displacement
            println(players[activeIndex] + " landed on a snake! They move back to square " + currentPosition)
        }
        ladders.firstOrNull { ladder -> ladder.position == currentPosition }?.let {
            currentPosition += it.displacement
            println(players[activeIndex] + " landed on a ladder! They move up to square " + currentPosition)
        }
        positions[players[activeIndex]] = currentPosition
        activeIndex = (activeIndex + 1) % players.size
        currentIteration++
    }
    println("Max iterations reached")
}