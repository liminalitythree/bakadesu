package lain

import java.io.File
import com.vdurmont.emoji.EmojiParser

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: bakadesu <filename>")
        return
    }

    val file = File(args[0])

    val text = file.readText()
    val emojis = EmojiParser.extractEmojis(text)
    println(emojis)

    val tokens = Lexer(emojis).scanTokens()
    val sexpr = Parser(tokens).parse()
    val res = Interpreter().eval(sexpr)
    println(res)
    println()
    res.print()
}
