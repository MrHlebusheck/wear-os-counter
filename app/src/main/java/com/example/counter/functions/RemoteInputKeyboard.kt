package com.example.counter.functions

import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender

typealias LauncherType = ManagedActivityResultLauncher<Intent, ActivityResult>

fun RemoteInputKeyboard(
    launcher: LauncherType,
    label: String,
    key: String,
    emojis: Boolean = true
) {
    val remoteInputs: List<RemoteInput> = listOf(
        RemoteInput.Builder(key)
            .setLabel(label)
            .wearableExtender {
                setEmojisAllowed(emojis)
                setInputActionType(EditorInfo.IME_ACTION_DONE)
            }.build(),
    )

    val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

    launcher.launch(intent)
}

//val launcher = rememberLauncherForActivityResult(
//    ActivityResultContracts.StartActivityForResult()
//) {
//    it.data?.let { data ->
//        val results: Bundle = RemoteInput.getResultsFromIntent(data)
//        val newInputText: CharSequence? = results.getCharSequence(key)
//        val userInput = newInputText?.toString() ?: ""
//        ACTION(userInput)
//    }
//}