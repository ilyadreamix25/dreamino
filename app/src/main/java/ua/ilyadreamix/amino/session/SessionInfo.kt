package ua.ilyadreamix.amino.session

data class SessionInfo(
    val lastLogin: Long = 0,
    val secret: String,
    val sessionId: String,
    val deviceId: String,
    val userId: String
)