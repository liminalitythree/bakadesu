package lain

import java.math.BigInteger

// handles the base-12 clock emoji numbers
class ClockNumbers {
    companion object {
        private val clocks = listOf(
            "\uD83D\uDD50",
            "\uD83D\uDD51",
            "\uD83D\uDD52",
            "\uD83D\uDD53",
            "\uD83D\uDD54",
            "\uD83D\uDD55",
            "\uD83D\uDD56",
            "\uD83D\uDD57",
            "\uD83D\uDD58",
            "\uD83D\uDD59",
            "\uD83D\uDD5A",
            "\uD83D\uDD5B"
        )

        fun isClock(c: String): Boolean {
            return clocks.contains(c)
        }

        fun parseClocks(input: List<String>): Int {

        }

        fun parseClocks(input: List<String>): Int {
            val q = input.reversed()
            var multiplier = 0
            var result = 0
            println(q)

            for (a in q) {
                if (!isClock(a)) {
                    throw NotAClockException("$a is not a clock.")
                }

                result += if (multiplier == 0) {
                    println("HI HIH IH HI I")
                    println(clocks.indexOf(a))
                    println("aaaa")
                    clocks.indexOf(a)
                }
                else {
                    println(clocks.indexOf(a) * multiplier * 12)
                    clocks.indexOf(a) * (multiplier * 12)
                }

                multiplier++
            }

            println(result)
            println(result.toString(10))
            println(result.toString(12))
            println(result.toString(12).toInt(12))
            println(result.toString(12).toInt(10))

            return result.toString(10).toInt()
        }
    }
}

class NotAClockException(message: String) : Exception(message)
