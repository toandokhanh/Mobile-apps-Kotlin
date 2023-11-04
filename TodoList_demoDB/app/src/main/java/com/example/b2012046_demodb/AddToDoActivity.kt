package com.example.b2012046_demodb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.b2012046_demodb.databinding.ActivityAddToDoBinding
import com.google.android.material.snackbar.Snackbar

class AddToDoActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddToDoBinding
    private lateinit var newsEntity: NewsEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    newsEntity= NewsEntity(0,title,desc)
                    newsDB.doa().insertNews(newsEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Tiêu đề và mô tả không được để trống !",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }

    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(this, NewsDatabase::class.java, Constants.NEWS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

}