package ua.ilyadreamix.amino.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object SessionUtility {

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    fun initContext(context: Context): SessionUtility {
        sharedPreferences = context.getSharedPreferences(
            SharedPreferencesKeys.NAME,
            Context.MODE_PRIVATE
        )
        return this
    }

    fun getStatus(): Int {

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
            gson.toJson(session, SessionInfo::class.java)
        )
        .apply()

    fun getSessionInfo(): SessionInfo? {
        val sessionJson = sharedPreferences.getString(
            SharedPreferencesKeys.SESSION,
            null
        ) ?: return null

        return gson.fromJson(sessionJson, SessionInfo::class.java)
    }

    private fun needRelogin(session: SessionInfo) =
        System.currentTimeMillis() > session.lastLogin + (24 * 60 * 60 * 1000)
}