package ua.ilyadreamix.amino.ui.action

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme
import ua.ilyadreamix.amino.ui.post.PostScreen

class ActionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.data

        setContent {
            AminoTheme {
                @Suppress("MoveVariableDeclarationIntoWhen")
                val actionName = uri!!.getQueryParameter(ACTION_NAME)!!
                when (actionName) {
                    ActionNames.POST -> PostScreen(intent)
                }
            }
        }
    }

    companion object {
        const val ACTION_NAME = "name"
        fun String.parseActionName() = "dreamino://action?$ACTION_NAME=$this"
    }
}