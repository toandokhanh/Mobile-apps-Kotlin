package com.example.b2012046_appchiasedieuuoc.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2012046_appchiasedieuuoc.Apis.Constants
import com.example.b2012046_appchiasedieuuoc.Models.RequestAddWish
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var fullName = ""
    private var content = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        // Khởi tạo SharedPreferences và lấy idUser từ SharedPreferences
        mAppSharedPreferences = AppSharedPreferences(requireContext())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()

        binding.apply {
            btnSave.setOnClickListener{
                if (edtFullName.text.isNotEmpty() && edtContent.text.isNotEmpty()){
                    fullName = edtFullName.text.toString().trim()
                    content = edtContent.text.toString().trim()
                    addWish(fullName, content)
                }
            }
        }

        return binding.root
    }

    fun addWish(fullName: String, content: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val response = Constants.getInstance()
                    .addWish(RequestAddWish(idUser, fullName, content))
                    .body()

                if (response == null) {
                    Toast.makeText(requireContext(), "Không thể kết nối với máy chủ", Toast.LENGTH_SHORT).show()
                } else {

                    if (response.success) {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
