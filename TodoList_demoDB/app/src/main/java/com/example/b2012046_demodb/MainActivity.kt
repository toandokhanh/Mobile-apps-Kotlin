package com.example.b2012046_demodb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.b2012046_demodb.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this, AddToDoActivity::class.java)
            startActivity(intent)
        }
    }
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(this,NewsDatabase::class.java, Constants.NEWS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
            if(newsDB.doa().getAllNews().isNotEmpty()){
                rvNewsList.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
                newsAdapter.differ.submitList(newsDB.doa().getAllNews())
                setupRecyclerView()
            }else{
                rvNewsList.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
            }
        }
    }
    private fun setupRecyclerView(){
        binding.rvNewsList.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=newsAdapter
        }
    }


}