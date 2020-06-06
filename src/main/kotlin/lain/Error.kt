package lain

import kotlin.system.exitProcess

fun error(line: Int, message: String) {
    System.err.println("[line $line] Error $message")
    exitProcess(1)
}
