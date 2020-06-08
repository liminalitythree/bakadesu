package lain

class Interpreter() {
    private val global = Environment()
    var environment = global

    init {
        global.set("\uD83D\uDD8D", DefineFun(this)) // 🖍
        global.set("\uD83E\uDD7A", UnionFun(this)) // 🥺
        global.set("\uD83D\uDECE️", IntersectionFun(this)) // 🛎️
        global.set("\uD83D\uDEC1", SetDiffFun(this)) // 🛁
        global.set("\uD83E\uDE95", SymDiffFun(this)) // 🪕
    }

    // evaluates a s expression... maybe
    fun eval(sexpr: Sexpr): RangeList {
        return sexpr(sexpr)
    }

    fun executeFunction(fn: FunctionValue, list: BList): RangeList {
        if ((list.list.size - 1) != fn.args.size) {
            error(888, "function arguments not the right amount of arguments at function ${list.list[0]}")
        }

        val prevenv = environment

        environment = Environment(environment)

        if (list.list.size > 1) {
            val inargs = list.list.subList(1, list.list.size).map { sexpr(it) }

            for ((i, a) in inargs.withIndex()) {
                environment.set(fn.args[i], RangeListValue(a))
            }
        }

        val ret = sexpr(fn.sexpr)

        environment = prevenv

        return ret
    }

    // evaluates an s-expression
    private fun sexpr(sexpr: Sexpr): RangeList {
        return when (sexpr) {
            is BList  -> blist(sexpr)
            is Symbol -> symbol(sexpr)
        }
    }

    // evaluates a BList
    private fun blist(list: BList): RangeList {
        // first element should be an operator
        if (list.list[0] !is Identifier) {
            error(-6969, "First element in list is not an identifier")
        }
        else {
            val ident = list.list[0] as Identifier

            val fn = environment.get(ident.name)
            if (fn == null) {
                error(-96, "Unidentified value $ident.name")
            }

            return when (fn) {
                is FunctionValue -> executeFunction(fn, list)
                is BuiltinFunction -> fn.execute(list)
                is RangeListValue -> throw Exception("uhhh you tried to execute a range list")
                else -> throw NullPointerException("there was a null pointer somewhere in there RIP lol")
            }
        }

        throw Exception("this is supposed to be unreachable code... how is this being executed???")
    }

    // evaluates a symbol
    private fun symbol(sexpr: Sexpr): RangeList {
        if (sexpr !is AstRange) {
            throw Exception("this should not be possible... probably")
        }

        val range = sexpr as AstRange

        return RangeList.from(range.range)
    }
}
