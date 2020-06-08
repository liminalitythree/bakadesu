package lain

import java.lang.NullPointerException

/* grammar:
s_expr      = symbol | list
list        = emoji_leftparen s_expr+ emoji_rightparen
symbol      = range | identifier
range       = "[" number "," number "]"
number      = [0-9]+
identifier  = emoji+
emoji       = : [a-z0-9_] :
 */

class Parser(private val tokens: List<Token>) {
    // points to current token in this.tokens
    private var current = 0

    // parses the input tokens
    // maybe
    fun parse(): Sexpr {
        return sexpr()
    }

    // s_expr = symbol | list
    private fun sexpr(): Sexpr {
        // if token is "(" its a list, otherwise its a symbol
        if (match(TokenType.LEFT_PAREN)) return list()

        return symbol()
    }

    // list = "(" s_expr+ ")"
    private fun list(): Sexpr {
        val start = previous()
        val list = mutableListOf<Sexpr>()

        list.add(sexpr())

        val a = peek()

        while (!match(TokenType.RIGHT_PAREN))
            list.add(sexpr())

        return BList(start, list.toList())
    }

    // symbol = range | operator
    private fun symbol(): Sexpr {
        // if token is "[", its a range, otherwise its an identifier
        // maybe
        if (match(TokenType.LEFT_BRACKET)) return range()

        return identifier()
    }

    private fun identifier(): Sexpr {
        val ident = consume(TokenType.IDENTIFIER, "Expected an identifier.")

        if (ident.literal != null) {
            return Identifier(ident, ident.literal as String)
        } else {
            throw NullPointerException("Identifier token had null literal.")
        }
    }

    // range = "[" number "," number "]"
    private fun range(): Sexpr {
        val start = peek()

        val min = number()
        consume(TokenType.COMMA, "Expected ',' in range expression")
        val max = number()
        consume(TokenType.RIGHT_BRACKET, "Expected closing ']' in range expression.")

        return AstRange(start, Range(min, max))
    }

    private fun number(): Int {
        val num = consume(TokenType.NUMBER, "Expected a number.")

        if (num.literal != null) {
            return num.literal as Int
        } else {
            throw NullPointerException("Number token had null literal maybe.")
        }
    }


    // if current token is any of the given types, consumes it and returns true
    // else, returns false
    // maybe
    private fun match(vararg tokenTypes: TokenType): Boolean {
        for (type in tokenTypes) {
            if (check(type)) {
                advance()
                return true
            }
        }

        return false
    }

    // if current token is of given type, return the token
    // else, cause an error with string message
    // maybe
    private fun consume(type: TokenType, message: String): Token {
        if (check(type)) return advance()

        error(-1, message)

        // this is never called as error() calls exitProcess
        // maybe
        return peek()
    }

    // peeks at the token and returns true if its type is type
    // else, returns false
    // maybe
    private fun check(type: TokenType): Boolean {
        if (isAtEnd()) return false
        return peek().type == type
    }

    // consumes the current token and returns it
    // maybe
    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    // returns true if we parser is at the end of input
    // maybe
    // else, returns false
    // maybe
    private fun isAtEnd(): Boolean {
        return peek().type == TokenType.EOF
    }

    // returns the current token without consuming it
    // maybe
    private fun peek(): Token {
        return tokens[current]
    }

    // returns the previous token
    // maybe
    private fun previous(): Token {
        return tokens[current - 1]
    }
}