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

        // subtract b from a, will return values that are in a but not in b
        fun subtract (a: Range, b: Range): List<Range> {
            if (!overlaps(a, b)) return listOf(a)

            // if b is completely inside a, eg it will split a into two ranges
            if (b.min > a.min &&  b.max < a.max) {
                return listOf(
                        Range(a.min, b.min - 1),
                        Range(b.max + 1, a.max)
                )
            }

            // if b is overlapping the top of a
            if (b.max >= a.max) return listOf(Range(a.min, b.min - 1))

            // else, b is overlapping the bottom  of a
            return listOf(Range(b.max + 1, a.max))
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
