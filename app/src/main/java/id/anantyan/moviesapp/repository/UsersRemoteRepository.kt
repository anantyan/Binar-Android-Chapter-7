package id.anantyan.moviesapp.repository

import id.anantyan.moviesapp.data.api.PhotoApi
import okhttp3.MultipartBody
import javax.inject.Inject

class UsersRemoteRepository @Inject constructor(
    private val photoApi: PhotoApi
) {
    suspend fun photoApi(photo: MultipartBody.Part) = photoApi.uploadImg(image = photo)
}