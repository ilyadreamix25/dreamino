package ua.ilyadreamix.amino.utility.coil

fun String.toSafeUrl() = this.replace("http", "https")