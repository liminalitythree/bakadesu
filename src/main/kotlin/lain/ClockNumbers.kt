package lain

import java.awt.Point
import java.math.BigInteger

// handles the base-12 clock emoji numbers
class ClockNumbers {
    companion object {
        private val clocks = mapOf(
            Pair("\uD83D\uDD50", "0"),
            Pair("\uD83D\uDD51", "1"),
            Pair("\uD83D\uDD52", "2"),
            Pair("\uD83D\uDD53", "3"),
            Pair("\uD83D\uDD54", "4"),
            Pair("\uD83D\uDD55", "5"),
            Pair("\uD83D\uDD56", "6"),
            Pair("\uD83D\uDD57", "7"),
            Pair("\uD83D\uDD58", "8"),
            Pair("\uD83D\uDD59", "9"),
            Pair("\uD83D\uDD5A", "a"),
            Pair("\uD83D\uDD5B", "b")
        )

        fun isClock(c: String): Boolean {
            return clocks.containsKey(c)
        }

        fun parseClocks(input: List<String>): Int {
            val str = input.subList(1, input.size).fold(clocks[input[0]]) {
                a, e -> a.plus(clocks[e])
            }

            if (str != null) {
                return str.toInt(12)
            } else {
                throw Exception("something bad happened in clock class")
            }
        }
    }
}

class NotAClockException(message: String) : Exception(message)
