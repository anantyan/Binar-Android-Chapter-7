package id.anantyan.moviesapp.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import id.anantyan.moviesapp.data.local.model.UsersLocal

interface ProfileDialogHelper {
    fun dialogSetPassword(listener: (item: UsersLocal, dialog: AlertDialog) -> Unit)
    fun dialogSetProfile(users: UsersLocal, listener: (item: UsersLocal, dialog: AlertDialog) -> Unit)
    fun dialogLogout(listener: (dialog: AlertDialog) -> Unit)
}

fun Context.dialog(): ProfileDialogHelper {
    val dialog: ProfileDialogHelper by lazy { ProfileDialog(this) }
    return dialog
}