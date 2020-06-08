package lain

// THIS IS NOT SOMETHING PERVERTED
// IT STANDS FOR S-EXPRESSION
// NOT SOMETHING WITH S*X IN IT
// maybe
sealed class Sexpr

// stands for baka list (since List is already taken by meanie standard library)
// maybe
// start is the token of the starting left paren (
// maybe
data class BList(val start: Token, val list: List<Sexpr>) : Sexpr()

sealed class Symbol : Sexpr()

data class Identifier(val token: Token, val name: String) : Symbol()

// since the normal range is already using Range... maybe
// maybe
// start is the token of the starting left bracket [
// maybe
data class AstRange(val start: Token, val range: Range) : Symbol()
