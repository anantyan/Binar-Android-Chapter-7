package id.anantyan.moviesapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileLocal(
    val resId: Int? = null,
    val title: String? = null,
    val field: String? = null
) : Parcelable
