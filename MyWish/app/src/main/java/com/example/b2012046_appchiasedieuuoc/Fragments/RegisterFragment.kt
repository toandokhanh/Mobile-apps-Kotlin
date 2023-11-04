package com.example.b2012046_appchiasedieuuoc.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2012046_appchiasedieuuoc.Apis.Constants
import com.example.b2012046_appchiasedieuuoc.Models.RequestRegisterOrLogin
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentLoginBinding
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            btnRegister.setOnClickListener{
                if(edtUsername.text.isNotEmpty()){
                    username = edtUsername.text.toString().trim()
                    registerUser(username)

                }else {
                    Snackbar.make(it, "Vui lòng nhập mã số sinh viên nhé!", Snackbar.LENGTH_LONG).show()
                }
            }

            tvLogin.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout,LoginFragment())
                    .commit()
            }
        }

        return binding.root
    }

    private fun registerUser(username: String) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val response = Constants.getInstance().registerUser(RequestRegisterOrLogin(username)).body()
                    if(response != null){
                        if(response.success){
                            // register successfully
                            mAppSharedPreferences.putIdUser("idUser", response.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, WishListFragment())
                                .commit()
                            progressBar.visibility = View.GONE
                        }else {
                            tvMessage.text = response.message
                            tvMessage.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}