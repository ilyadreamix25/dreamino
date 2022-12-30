package ua.ilyadreamix.amino.session

import android.content.Context
import com.google.gson.Gson

class SessionUtility(context: Context) {
    private val gson = Gson()
    private val sharedPreferences =
        context.getSharedPreferences(
            SharedPreferencesKeys.NAME,
            Context.MODE_PRIVATE
        )

    fun getStatus(): Int {
        val session = getSessionInfo()

        return if (session == null)
            SessionStatus.NOT_AUTHORIZED
        else if (checkNotRelogin(session))
            SessionStatus.EXPIRED
        else
            SessionStatus.ACTIVE
    }

    private fun getSessionInfo(): SessionInfo? {
        val sessionJson = sharedPreferences.getString(
            SharedPreferencesKeys.SESSION,
            null
        ) ?: return null

        return gson.fromJson(sessionJson, SessionInfo::class.java)
    }

    private fun checkNotRelogin(session: SessionInfo) =
        session.lastLogin + (24 * 3600 * 1000) >= System.currentTimeMillis()
}