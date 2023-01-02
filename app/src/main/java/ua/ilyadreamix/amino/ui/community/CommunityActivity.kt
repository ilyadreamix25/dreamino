package ua.ilyadreamix.amino.ui.community

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme

class CommunityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ndcId = intent.extras!!.getInt("ndcId")

        setContent {
            AminoTheme {
                CommunityScreen(ndcId = ndcId)
            }
        }
    }
}