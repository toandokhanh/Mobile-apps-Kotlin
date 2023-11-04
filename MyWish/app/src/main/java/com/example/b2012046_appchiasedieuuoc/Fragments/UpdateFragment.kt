package com.example.b2012046_appchiasedieuuoc.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.b2012046_appchiasedieuuoc.Apis.Constants
import com.example.b2012046_appchiasedieuuoc.Models.RequestUpdateWish
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentAddBinding
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    private var idWish =""
    private var fullName = ""
    private var content = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        //khoi tao  mAppSharedPreferences va luu idUser, wish tu  mAppSharedPreferences
        mAppSharedPreferences = AppSharedPreferences(requireContext())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()
        idWish= mAppSharedPreferences.getWish("idWish").toString()
        fullName=mAppSharedPreferences.getWish("fullName").toString()
        content=mAppSharedPreferences.getWish("content").toString()

        //thiet lap noi dung giao dien
        binding.edtFullName.setText(fullName)
        binding.edtContent.setText(content)

        binding.apply {
            btnSave.setOnClickListener {
                if (edtFullName.text.isNotEmpty() && edtContent.text.isNotEmpty()) {
                    fullName =edtFullName.text.toString().trim()
                    content = edtContent.text.toString().trim()
                    //thuc hien call api the dieu uoc
                    updateWish(fullName, content)
                }
            }
        }
        return binding.root
    }

    private fun updateWish(fullName: String, content: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val response = Constants.getInstance()
                    .updateWish(RequestUpdateWish(idUser,idWish,fullName,content))
                    .body()

                if(response != null){
                    if (response.success) {
                        binding.progressBar.visibility = View.GONE
                        //cap nhat dieu uoc thanh cong
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    } else {
                        binding.progressBar.visibility = View.GONE
                        //cap nhat dieu uoc khong thanh cong
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    }
                }
            }
        }
    }

}