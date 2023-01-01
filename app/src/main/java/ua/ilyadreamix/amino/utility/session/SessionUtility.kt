package ua.ilyadreamix.amino.utility.session

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SessionUtility {
    private lateinit var sharedPreferences: SharedPreferences
    private val json = Json { ignoreUnknownKeys = true }

    fun initContext(context: Context): SessionUtility {
        sharedPreferences = context.getSharedPreferences(
            SharedPreferencesKeys.NAME,
            Context.MODE_PRIVATE
        )
        return this
    }

    fun getStatus(): SessionStatus {
        val session = getSessionInfo()
        return if (session == null)
            SessionStatus.NOT_AUTHORIZED
        else if (needRelogin(session))
            SessionStatus.EXPIRED
        else
            SessionStatus.ACTIVE
    }

    fun saveSession(session: SessionInfo) = sharedPreferences.edit()
        .putString(
            SharedPreferencesKeys.SESSION,
            json.encodeToString(session)
        )
        .apply()

    fun getSessionInfo(): SessionInfo? {
        val sessionJson = sharedPreferences.getString(
            SharedPreferencesKeys.SESSION,
            null
        ) ?: return null

        return json.decodeFromString(sessionJson)
    }

    fun deleteSession() = sharedPreferences.edit()
        .clear()
        .apply()

    private fun needRelogin(session: SessionInfo) =
        System.currentTimeMillis() > session.lastLogin + (24 * 60 * 60 * 1000)
}