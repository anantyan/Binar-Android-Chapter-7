package id.anantyan.moviesapp.data.api

import id.anantyan.moviesapp.model.Photo
import id.anantyan.utils.Constant
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PhotoApi {
    @Multipart
    @POST("upload")
    suspend fun uploadImg(
        @Query("key") apiKey: String? = Constant.API_KEY_UPLOAD,
        @Part image: MultipartBody.Part,
    ): Response<Photo>
}