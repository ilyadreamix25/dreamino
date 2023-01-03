package ua.ilyadreamix.amino.utility.string

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

fun Double.toAbbreviatedString(): String {
    val charA = 'a'.code
    val units = mapOf(
        0 to "",
        1 to "K",
        2 to "M",
        3 to "B",
        4 to "T"
    )

    if (this < 1.0) {
        return "0"
    }

    val n = log(this, 1000.0).toInt()
    val m = this / 1000.0.pow(n)

    val unit = if (n < units.count()) {
        units[n]!!
    } else {
        val unitInt = n - units.count()
        val secondUnit = unitInt % 26
        val firstUnit = unitInt / 26
        (firstUnit + charA).toChar().toString() + (secondUnit + charA).toChar().toString()
    }

    return DecimalFormat("#.#").format(floor(m * 100) / 100) + unit
}