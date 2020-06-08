package lain

enum class TokenType {
    // single-character tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACKET, RIGHT_BRACKET, COMMA,

    // Literals
    NUMBER,

    IDENTIFIER,

    EOF
}

class Token(val type: TokenType, val lexeme: String, val literal: Any?) {
    override fun toString(): String {
        return "$type $lexeme $literal"
    }
}
