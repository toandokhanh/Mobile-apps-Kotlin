package com.example.b2012046_demodb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsEntity: NewsEntity)

    @Update
    fun updateNews(newsEntity: NewsEntity)

    @Delete
    fun deleteNews(newsEntity: NewsEntity)

    @Query("SELECT * FROM ${Constants.NEWS_TABLE} ORDER BY newsId DESC")
    fun getAllNews() : MutableList<NewsEntity>

    @Query("SELECT * FROM ${Constants.NEWS_TABLE} WHERE newsId LIKE :id")
    fun getNews(id : Int) : NewsEntity
}
