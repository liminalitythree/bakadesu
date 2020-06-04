package lain

data class Range(val min:Int, val max: Int) {
    companion object {
        fun and(a:Range, b:Range):Range {
            val min = if (a.min <= b.min) b.min
            else a.min

            val max = if (a.max >= b.max) b.max
            else a.max

            return Range(min, max)
        }

        fun or(a:Range, b:Range):Range {
            // lowest min, highest max
            val min = if (a.min <= b.min) a.min
            else b.min

            val max = if (a.max <= b.max) b.max
            else a.max

            return Range(min, max)
        }

        // returns true if the two input ranges overlap, false if they don't
        fun overlaps(a: Range, b:Range):Boolean {
            if (a.min > b.max) return false
            if (b.min > a.max) return false
            return true
        }
    }

    fun isEmpty():Boolean {
        if (this.min >= this.max) return true
        return false
    }
}

data class RangeList(val list: List<Range>) {
    companion object {
        fun from(list: List<Range>):RangeList {
            val first = RangeList(listOf(list[0]))
            val rest = list.subList(1, list.size)

            return rest.fold(first) { a, e -> a.plus(e) }
        }

        fun fromRange(range: Range):RangeList {
            return RangeList(listOf(range))
        }
    }

    fun plus(range: Range): RangeList {
        // list of elements in the rangelist that overlap with range
        val (match, rest) = this.list.partition { Range.overlaps(it, range) }
        val c:Range = match.fold(range) { a, e -> Range.or(a, e) }
        return RangeList(rest.plus(c))
    }
}



fun main(args: Array<String>) {
    println(
            RangeList.from(
                    listOf(
                        Range(0, 15),
                        Range(3, 36),
                        Range(-5, -3),
                        Range(400, 500)
                    )
            )
    )
}
