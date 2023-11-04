package com.example.b2012046_appchiasedieuuoc.Fragments

import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2012046_appchiasedieuuoc.Apis.Constants
import com.example.b2012046_appchiasedieuuoc.Models.RequestRegisterOrLogin
import com.example.b2012046_appchiasedieuuoc.R
import com.example.b2012046_appchiasedieuuoc.Sharedpreferences.AppSharedPreferences
import com.example.b2012046_appchiasedieuuoc.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        mAppSharedPreferences = AppSharedPreferences(requireContext())


        binding.apply {
            tvRegister.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, RegisterFragment())
                    .commit()
            }


            btnLogin.setOnClickListener{
                if(edtUsername.text.isNotEmpty()){
                    username = edtUsername.text.toString().trim()
                    loginUser(username)
                }
            }
        }
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_login, container, false)
        return binding.root
    }

    private fun loginUser(username: String) {
        Log.d("Toandokhanh", "loginUser: LoginFragment")
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val response = Constants.getInstance().loginUser(RequestRegisterOrLogin(username)).body()
                    if (response != null){
                        if (response.success){
                            Log.d("Toandokhanh", "loginUser: LoginFragment: Response Success !")
                            mAppSharedPreferences.putIdUser("idUser", response.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame_layout, WishListFragment()).commit()
                            progressBar.visibility = View.GONE
                        }else{
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