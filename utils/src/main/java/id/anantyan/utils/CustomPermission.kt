package id.anantyan.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import id.anantyan.utils.Constant.PERMISSION_CAMERA_AND_WRITE_EXTERNAL
import id.anantyan.utils.permission.EasyPermissions

fun requestCameraAndWriteExternalPermission(host: Fragment) {
    EasyPermissions.requestPermissions(
        host,
        "Fitur ini tidak akan bisa jalan tanpa adanya izin kamera!",
        PERMISSION_CAMERA_AND_WRITE_EXTERNAL,
        perms = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    )
}