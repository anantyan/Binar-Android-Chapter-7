package id.anantyan.moviesapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplications : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .placeholder(R.drawable.ic_outline_image_24)
            .error(R.drawable.ic_outline_image_not_supported_24)
            .build()
    }
}