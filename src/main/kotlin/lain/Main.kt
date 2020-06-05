package lain

fun main(args: Array<String>) {

    println (
        RangeList.setDiff(
                RangeList.from(listOf(
                        Range(0, 23),
                        Range(34, 56),
                        Range(58, 95),
                        Range(-55, -31)
                )),
                RangeList.from(listOf(
                        Range(-5, 15),
                        Range(40, 65),
                        Range(90, 230),
                        Range(6003, 58923)
                ))
        )
    )

}
