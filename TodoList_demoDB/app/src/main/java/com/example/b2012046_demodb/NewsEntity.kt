package com.example.b2012046_demodb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.NEWS_TABLE)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val newsId :Int,
    @ColumnInfo(name = "news_title")
    val newsTitle:String,
    @ColumnInfo(name = "news_desc")
    val newsDesc : String
)
