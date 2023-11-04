package com.example.b2012046_appchiasedieuuoc.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.akuro.mywish.adapters.WishAdapter
import com.example.b2012046_appchiasedieuuoc.Apis.Constants
import com.example.b2012046_appchiasedieuuoc.Models.RequestDeleteWish
import com.example.b2012046_appchiasedieuuoc.Models.Wish
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentWishListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WishListFragment : Fragment() {
    private lateinit var binding: FragmentWishListBinding
    private lateinit var mWishList: ArrayList<Wish>
    private lateinit var mWishAdapter: WishAdapter
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishListBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences va lay ra idUser
        mAppSharedPreferences = AppSharedPreferences(requireActivity())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()

        //khoi tao mWishList
        mWishList = ArrayList()

        //thuc hien call api lay danh sach dieu uoc
        getWishList()

        binding.btnAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AddFragment())
                .commit()
        }

//Infalte the layout for this fragment
        return binding.root
    }

    private fun getWishList() {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val response = Constants.getInstance().getWishList().body()
                if (response != null) {
                    mWishList.addAll(response)
                    //khoi tao Apdapter va thiet lap giao dien
                    initAdapterAndSetLayout()
                }
            }
        }
    }
    private fun initAdapterAndSetLayout() {
        if (context == null) {
            return
        }
        mWishAdapter = WishAdapter(requireActivity(), mWishList, mAppSharedPreferences,
            object : WishAdapter.IClickItemWish{
                override fun onClickUpdate(idWish: String, fullName: String, content: String) {
                    // luu thong tin dieu uoc vao mAppSharedPreferences va chuyen vao man hinh cap nhat dieu uoc
                    mAppSharedPreferences.putWish("idWish", idWish)
                    mAppSharedPreferences.putWish("fullName", fullName)
                    mAppSharedPreferences.putWish("content", content)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, UpdateFragment())
                        .commit()
                }

                override fun onClickRemove(idWish: String) {
                    // thuc hien call api xoa dieu uoc
                    deleteWish(idWish)
                }
            })

        binding.rcvWishList.adapter =  mWishAdapter
        binding.rcvWishList.layoutManager = LinearLayoutManager(requireActivity())
        binding.progressBar.visibility = View.GONE
    }


    private fun deleteWish(idWish: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
    withContext(Dispatchers.Main){
        val response = Constants.getInstance().deleteWish(RequestDeleteWish(idUser, idWish))
            .body()
        if(response !=null){
            if (response.success) {
                binding.progressBar.visibility = View.GONE
                // xoa dieu uoc thanh cong
                Toast.makeText(requireContext(),response.message, Toast.LENGTH_SHORT).show()
                // Load lai man hinh wishlist
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, WishListFragment())
                    .commit()
            } else {
                binding.progressBar.visibility = View.GONE
                // Xoa dieu uoc k thanh cong
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