package com.example.counter.screens.counter_displays

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.counter.R
import com.example.counter.database.Counter
import com.example.counter.functions.LauncherType
import com.example.counter.functions.RemoteInputKeyboard
import com.example.counter.navigation.Screen
import com.example.counter.screens.RENAME_KEY
import com.example.counter.screens.TARGET_KEY
import com.example.counter.ui.RoundedButton

@Composable
fun DisplayOptions(
    counter: Counter?,
    targetLauncher: LauncherType,
    renameLauncher: LauncherType,
    navController: NavController
) {
    if (counter == null) return

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        Modifier.size(screenWidth, screenHeight),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(painterResource(R.drawable.target_arrow), "Target") {
                RemoteInputKeyboard(
                    targetLauncher,
                    "Set new target",
                    TARGET_KEY,
                    emojis = false
                )
            }
            RoundedButton(
                painterResource(R.drawable.round_drive_file_rename_outline_24),
                "Rename"
            ) {
                RemoteInputKeyboard(
                    renameLauncher,
                    "Set new name",
                    RENAME_KEY
                )
            }
        }
        RoundedButton(painterResource(R.drawable.trash), "Delete") {
            navController.navigate(Screen.Delete.passId(counter.id))
        }
    }
}

@Preview(device = "id:Samsung watch 6 classic", showBackground = true)
@Composable
fun OptionsPreview() {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}
    DisplayOptions(Counter("Preview"), launcher, launcher, rememberNavController())
}
