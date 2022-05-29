package id.anantyan.moviesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.anantyan.moviesapp.data.local.MoviesDao
import id.anantyan.moviesapp.data.local.UsersDao
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.moviesapp.data.local.model.UsersLocal

@Database(entities = [
    UsersLocal::class,
    MoviesLocal::class
], version = 3, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun usersDao(): UsersDao
    abstract fun moviesDao(): MoviesDao
}