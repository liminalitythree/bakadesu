package lain

import java.time.Clock

class Lexer(val source: List<String>) {
    private val tokens = mutableListOf<Token>()
    private var start = 0
    private var current = 0

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            // we are at the beginning of the next lexeme
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null))

        return tokens.toList()
    }

    // scan one token maybe
    private fun scanToken() {
        val c:String = advance()
        when (c) {
            "\uD83E\uDD1C" -> addToken(TokenType.LEFT_PAREN)
            "\uD83E\uDD1B" -> addToken(TokenType.RIGHT_PAREN)
            "\uD83D\uDC49" -> addToken(TokenType.LEFT_BRACKET)
            "\uD83D\uDC48" -> addToken(TokenType.RIGHT_BRACKET)
            "〰" -> addToken(TokenType.COMMA)

            else -> {
                if (isDigit(c)) number()
                else identifier(c)
            }
        }

    }

    // turns [0-9]+ into a token and adds it to token list
    private fun number() {
        while (ClockNumbers.isClock(peek())) advance()

        addToken(TokenType.NUMBER, ClockNumbers.parseClocks(source.subList(start, current)))
    }

    // identifier is just 1 emoji
    private fun identifier(c: String) {
        addToken(TokenType.IDENTIFIER, c)
    }

    // consumes current character if matches expected, else returns false
    private fun match(expected: String): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false

        current++
        return true
    }

    // returns character without consuming it
    private fun peek(): String {
        if (isAtEnd()) return "\u0000"
        return source[current]
    }

    // returns true if c is [0-9]
    private fun isDigit(c: String): Boolean {
        return ClockNumbers.isClock(c)
    }

    // returns true if at end of string source, false if not
    private fun isAtEnd(): Boolean {
        return current >= source.size
    }

    // advance the pointer and return the new current char
    private fun advance(): String {
        current++
        return source[current - 1]
    }

    // adds a token to the list of tokens
    private fun addToken(type: TokenType, literal: Any?) {
        val text =  source.subList(start, current).reduce { a, e -> a.plus(e) }
        tokens.add(Token(type, text, literal))
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }
}