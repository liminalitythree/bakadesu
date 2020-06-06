package lain

class Interpreter() {

    // evaluates a s expression... maybe
    fun eval(sexpr: Sexpr): RangeList {
        return sexpr(sexpr)
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
        if (list.list[0] !is Operator)
            error(list.start.line, "First element in list is not an operator")
        else {
            val op = list.list[0] as Operator

            val ranges = mutableListOf<RangeList>()
            for (a in list.list.subList(1, list.list.size)) {
                ranges.add(sexpr(a))
            }

            return when (op.token.type) {
                TokenType.UNION         -> union(ranges)
                TokenType.INTERSECTION  -> intersection(ranges)
                TokenType.SETDIFF       -> setdiff(ranges)
                TokenType.SYMDIFF       -> symdiff(ranges)
                else -> throw Exception("this shouldn't happen... maybe")
            }
        }

        throw Exception("this is supposed to be unreachable code... how is this being executed???")
    }

    // evaluates a union
    private fun union(ranges: List<RangeList>): RangeList {
        return ranges.reduce { a, e -> RangeList.union (a, e) }
    }

    // evaluates an intersection
    private fun intersection(ranges: List<RangeList>): RangeList {
        return ranges.reduce { a, e -> RangeList.union (a, e) }
    }

    // evaluates a set difference
    private fun setdiff(ranges: List<RangeList>): RangeList {
        return ranges.reduce { a, e -> RangeList.setDiff(a, e) }
    }

    // evaluates a symmetric difference
    private fun symdiff(ranges: List<RangeList>): RangeList {
        return ranges.reduce { a, e -> RangeList.symmetricDiff(a, e) }
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
