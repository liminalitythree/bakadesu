package lain

data class RangeList(val list: List<Range>) {
    companion object {
        // creates a rangelist from input range
        // merges ranges that overlap
        fun from(list: List<Range>):RangeList {
            val first = RangeList(listOf(list[0]))
            val rest = list.subList(1, list.size)

            return rest.fold(first) { a, e -> a.plus(e) }
        }

        // all values that are a member of a, b, or both
        fun union(a: RangeList, b: RangeList): RangeList {
            return from(a.list.plus(b.list))
        }

        // all values that are a member of both a and b
        fun intersection(a: RangeList, b: RangeList): RangeList {
            val aover = a.list.filter { b.hasOverlap(it) }
            val bover = b.list.filter { a.hasOverlap(it) }

            val both = aover.plus(bover)

            val first = RangeList(listOf(both[0]))
            val rest = both.subList(1, both.size)

            return rest.fold(first) { q, e -> plusAnd(q, e) }
        }

        // All values of u that are not values of a
        fun setDiff (u: RangeList, a: RangeList): RangeList {
            val (match, rest) = u.list.partition { a.hasOverlap(it) }
            val amatch = a.list.filter { u.hasOverlap(it) }

            // rest are all not values of a
            // just need to check match now...
            // maybe
            val res = match.map {
                val muta = mutableListOf<List<Range>>()
                for (q in amatch)
                    if (Range.overlaps(it, q))
                        muta.add(Range.subtract(it, q))
                muta.toList()
            }.flatten().flatten()

            return RangeList(res.subList(1, res.size).fold(RangeList(listOf(res[0]))) {
                q, e -> plusAnd(q, e)
            }.list.plus(rest))
        }

        // Values that are in either a or b, but not both
        fun symmetricDiff(a: RangeList, b: RangeList): RangeList {
            // union minus intersection
            return setDiff(union(a, b), intersection(a, b))
        }

        // idk how this works but uhh i think it does work...
        // maybe
        fun plusAnd (a: RangeList, b: Range): RangeList {
            val (m, r) = a.list.partition { Range.overlaps(it, b) }
            val c:Range = m.fold(b) { q, e -> Range.and(q, e) }
            return RangeList(r.plus(c))
        }
    }

    // returns a rangelist that is this list with range element added
    // merges ranges if there is an overlap with range
    fun plus(range: Range): RangeList {
        // list of elements in the rangelist that overlap with range
        val (match, rest) = this.list.partition { Range.overlaps(it, range) }
        val c:Range = match.fold(range) { a, e -> Range.or(a, e) }
        return RangeList(rest.plus(c))
    }

    // sorts the rangelist and returns it
    fun sort(): RangeList {
        return RangeList(this.list.sortedBy { it.min })
    }

    // returns true if range overlaps with any member of this
    fun hasOverlap(range: Range): Boolean {
        for (e in this.list)
            if (Range.overlaps(e, range)) return true
        return false
    }
}
