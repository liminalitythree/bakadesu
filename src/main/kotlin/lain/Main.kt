package lain

fun main(args: Array<String>) {
    val a = RangeList.from(
        listOf(
            Range(0, 15),
            Range(17, 35),
            Range(67, 92)
        )
    )

    val b = RangeList.from(
        listOf(
            Range(3, 6),
            Range(30, 50),
            Range(60, 90),
            Range(100, 200)
        )
    )
    println (
        RangeList.intersection(a, b)
    )

}
