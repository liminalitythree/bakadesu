package lain

class Lexer(val source: String) {
    private val tokens = mutableListOf<Token>()
    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            // we are at the beginning of the next lexeme
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null, line))

        return tokens.toList()
    }

    // scan one token maybe
    private fun scanToken() {
        when (val c: Char = advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '[' -> addToken(TokenType.LEFT_BRACKET)
            ']' -> addToken(TokenType.RIGHT_BRACKET)
            ',' -> addToken(TokenType.COMMA)

            '+' -> addToken(TokenType.UNION)
            '&' -> addToken(TokenType.INTERSECTION)
            '-' -> addToken(TokenType.SETDIFF)
            '^' -> addToken(TokenType.SYMDIFF)

            // ignore whitespace
            ' ' -> {}
            '\r' -> {}
            '\t' -> {}

            '\n' -> line++

            else -> {
                if (isDigit(c)) number()
                else error(line, "Unexpected character '$c'.")
            }
        }

    }

    // turns [0-9]+ into a token and adds it to token list
    private fun number() {
        while (isDigit(peek())) advance()

        addToken(TokenType.NUMBER, source.substring(start, current).toInt())
    }

    // consumes current character if matches expected, else returns false
    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false

        current++
        return true
    }

    // returns character without consuming it
    private fun peek(): Char {
        if (isAtEnd()) return '\u0000'
        return source[current]
    }

    // returns true if c is [0-9]
    private fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    // returns true if at end of string source, false if not
    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    // advance the pointer and return the new current char
    private fun advance(): Char {
        current++
        return source[current - 1]
    }

    // adds a token to the list of tokens
    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }
}