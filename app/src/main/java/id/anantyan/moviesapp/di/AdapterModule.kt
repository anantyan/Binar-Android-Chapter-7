package id.anantyan.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.anantyan.moviesapp.data.local.UsersDao
import id.anantyan.moviesapp.database.RoomDB
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.moviesapp.ui.main.favorite.FavoriteAdapter
import id.anantyan.moviesapp.ui.main.favorite.FavoriteAdapterHelper
import id.anantyan.moviesapp.ui.main.home.HomeAdapter
import id.anantyan.moviesapp.ui.main.home.HomeAdapterHelper
import id.anantyan.moviesapp.ui.main.home_detail.CasterAdapter
import id.anantyan.moviesapp.ui.main.home_detail.GenresAdapter
import id.anantyan.moviesapp.ui.main.home_detail.HomeDetailAdapterHelper
import id.anantyan.moviesapp.ui.main.profile.ProfileAdapter
import id.anantyan.moviesapp.ui.main.profile.ProfileAdapterHelper
import id.anantyan.utils.Constant
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideAdapterTrending(repository: MoviesLocalRepository): HomeAdapterHelper {
        return HomeAdapter(repository)
    }

    @Singleton
    @Provides
    @Named(Constant.CAT_POPULAR)
    fun provideAdapterPopular(repository: MoviesLocalRepository): HomeAdapterHelper {
        return HomeAdapter(repository)
    }

    @Singleton
    @Provides
    @Named(Constant.CAT_TOP_RATED)
    fun provideAdapterTopRated(repository: MoviesLocalRepository): HomeAdapterHelper {
        return HomeAdapter(repository)
    }

    @Singleton
    @Provides
    @Named(Constant.CAT_NOW_PLAYING)
    fun provideAdapterNowPlaying(repository: MoviesLocalRepository): HomeAdapterHelper {
        return HomeAdapter(repository)
    }

    @Singleton
    @Provides
    @Named(Constant.CAT_UPCOMING)
    fun provideAdapterUpComing(repository: MoviesLocalRepository): HomeAdapterHelper {
        return HomeAdapter(repository)
    }

    @Singleton
    @Provides
    fun provideAdapterFavorite(repository: MoviesLocalRepository): FavoriteAdapterHelper {
        return FavoriteAdapter(repository)
    }

    @Singleton
    @Provides
    fun provideAdapterProfile(profileAdapter: ProfileAdapter): ProfileAdapterHelper {
        return profileAdapter
    }

    @Singleton
    @Provides
    fun adapterCaster(casterAdapter: CasterAdapter): HomeDetailAdapterHelper.Caster {
        return casterAdapter
    }

    @Singleton
    @Provides
    fun adapterGenres(genresAdapter: GenresAdapter): HomeDetailAdapterHelper.Genres {
        return genresAdapter
    }
}