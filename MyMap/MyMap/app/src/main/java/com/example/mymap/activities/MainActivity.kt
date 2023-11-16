package com.example.mymap.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymap.MapsAdapter
import com.example.mymap.Models.Place
import com.example.mymap.Models.UserMap
import com.example.mymap.R
import com.example.mymap.Utils
import com.example.mymap.databinding.ActivityMainBinding
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
     lateinit var binding : ActivityMainBinding
     private lateinit var userMaps : MutableList<UserMap>
     lateinit var mapAdapter: MapsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        //getSupportActionBar()?.show()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //tao layout manager cho rvMaps
        binding.rvMaps.layoutManager = LinearLayoutManager(this)

        //tao adapter cho rvMaps
        userMaps = generationSimpleData().toMutableList()
        mapAdapter = MapsAdapter(this, userMaps,
            object : MapsAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    Log.i(TAG, "onItemClick $position")
                    val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                    intent.putExtra(Utils.EXTRA_USER_MAP, userMaps[position])
                    startActivity(intent)
                }
            })

        binding.rvMaps.adapter = mapAdapter

        binding.btnadd.setOnClickListener {
            val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
            AlertDialog.Builder(this)
                .setTitle("Map title")
                .setView(mapFormView)
                .setNegativeButton("Cancle", null)
                .setPositiveButton("OK") { _,_ ->
                    val _title = mapFormView.findViewById<EditText>(R.id.et_title).text.toString()
                    Log.i(TAG, _title)
                    if(_title.trim().isEmpty()) {
                        Toast.makeText(this, "Fill out title", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
                    intent.putExtra(Utils.EXTRA_USER_TITLE, _title)
                    getResult.launch(intent)
                }
                .show()
        }
    }

    // Đăng ký một hợp đồng (contract) để theo dõi kết quả từ hoạt động con khi nó hoàn thành
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Hàm này được gọi khi hoạt động con hoàn thành và trả về kết quả
        if (result.resultCode == Activity.RESULT_OK) {
            // Xử lý khi hoạt động con thành công
            // Lấy dữ liệu trả về từ intent
            val userMap = result.data?.getSerializableExtra(Utils.EXTRA_USER_MAP) as UserMap
            // Thêm dữ liệu vào danh sách userMaps
            userMaps.add(userMap)
            // Cập nhật giao diện thông qua adapter
            // Điều này đảm bảo rằng RecyclerView hiển thị mục mới
            mapAdapter.notifyItemInserted(userMaps.size - 1)
        }
    }

    private fun generationSimpleData() : List<UserMap> {
        return listOf(
            UserMap(
                "Ẩm Thực",
                listOf(
                    Place("The 80's icafe", "Đường Mạc Thiên Tích", 10.0286827, 105.7732964),
                    Place("Trà sữa Tigon", "Đường Mạc Thiên Tích", 10.0278105, 105.7718373),
                    Place("Cafe Thủy Mộc", "Đường 3/2", 10.0273775, 105.7704913)
                )
            ),
            UserMap(
                "Đại Học Cần Thơ 2",
                listOf(
                    Place("Trường CNTT&TT", "thuộc ĐH Cần Thơ", 10.0308541, 105.768986),
                    Place("Trường Nông Nghiệp", "thuộc ĐH Cần Thơ", 10.0302655, 105.7679642),
                    Place("Hội Trường Rùa", "nơi tổ chức các hoạt động...", 10.0293402, 105.7690273)
                )
            )
        )
    }
}