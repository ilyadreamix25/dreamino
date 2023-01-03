package ua.ilyadreamix.amino.utility.string

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import java.util.Locale

fun String.fromAminoToLocalDate(locale: androidx.compose.ui.text.intl.Locale): String {
    try {
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.forLanguageTag(locale.toLanguageTag())
        )
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(this.substring(0 until this.length - 1))
        val dateFormatter = SimpleDateFormat(
            "MM.dd HH:mm",
            Locale.forLanguageTag(locale.toLanguageTag())
        )
        dateFormatter.timeZone = TimeZone.getDefault()
        return dateFormatter.format(value)
    } catch (e: Exception) {
        Log.e("AminoTimestamp", "Conversion error", e)
        return "00.00 00:00"
    }
}