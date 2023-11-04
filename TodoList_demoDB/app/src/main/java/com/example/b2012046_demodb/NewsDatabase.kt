package com.example.b2012046_demodb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun doa():NewsDao
}
