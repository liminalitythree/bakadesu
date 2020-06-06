package lain

import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: bakadesu <filename>")
        return
    }

    val file = File(args[0])

    val tokens = Lexer(file.readText()).scanTokens()
    val sexpr = Parser(tokens).parse()
    Interpreter().eval(sexpr).print()
}
