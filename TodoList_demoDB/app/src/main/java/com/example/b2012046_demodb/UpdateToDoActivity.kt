package com.example.b2012046_demodb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.b2012046_demodb.databinding.ActivityUpdateToDoBinding
import com.google.android.material.snackbar.Snackbar

class UpdateToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateToDoBinding
    private lateinit var newsEntity: NewsEntity
    private var newsId = 0
    private var defaultTitle = ""
    private var defaultDesc = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            newsId = it.getInt(Constants.BUNDLE_NEWS_ID)
        }
        binding.apply {
            defaultTitle=newsDB.doa().getNews(newsId).newsTitle
            defaultDesc=newsDB.doa().getNews(newsId).newsDesc

            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)
            btnDelete.setOnClickListener {
                newsEntity= NewsEntity(newsId,defaultTitle,defaultDesc)
                newsDB.doa().deleteNews(newsEntity)
                finish()
                startActivity(Intent(this@UpdateToDoActivity, MainActivity::class.java))
            }
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc=edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    newsEntity= NewsEntity(newsId,title,desc)
                    newsDB.doa().updateNews(newsEntity)
                    finish()
                    startActivity(Intent(this@UpdateToDoActivity, MainActivity::class.java))
                }
                else{
                    Snackbar.make(it,"Tiêu đề và mô tả không được để trống !", Snackbar.LENGTH_LONG).show()
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
