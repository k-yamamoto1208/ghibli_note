package com.example.ghiblinote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ghiblinote.data.Film

@Database(entities = [Film::class], version = 1)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}