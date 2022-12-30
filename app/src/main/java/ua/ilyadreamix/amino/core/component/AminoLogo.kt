package ua.ilyadreamix.amino.core.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ua.ilyadreamix.amino.R

@Composable
fun AminoLogo(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(R.drawable.ic_amino_logo),
        contentDescription = null,
        modifier = modifier
    )
}