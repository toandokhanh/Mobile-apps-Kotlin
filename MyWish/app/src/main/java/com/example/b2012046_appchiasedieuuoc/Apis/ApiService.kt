package com.example.b2012046_appchiasedieuuoc.Apis

import com.example.b2012046_appchiasedieuuoc.Models.RequestAddWish
import com.example.b2012046_appchiasedieuuoc.Models.RequestDeleteWish
import com.example.b2012046_appchiasedieuuoc.Models.RequestRegisterOrLogin
import com.example.b2012046_appchiasedieuuoc.Models.RequestUpdateWish
import com.example.b2012046_appchiasedieuuoc.Models.ResponseMessage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.net.CacheRequest
import com.example.b2012046_appchiasedieuuoc.Models.UserRespose
import com.example.b2012046_appchiasedieuuoc.Models.Wish
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.Path


class Constants {

    companion object {

        private const val BASE_URL = "https://bestwishesserver-production.up.railway.app/api/"
        private const val BASE_URL_BACKUP = "https://bestwishes-ct274.vercel.app/api/"

        fun getInstance(): ApiService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService::class.java)
        }
    }
}


interface ApiService {
    @POST("users/register")
    suspend fun registerUser(
        @Body request: RequestRegisterOrLogin
    ): Response<UserRespose>

    @POST("users/login")
    suspend fun loginUser(
        @Body request: RequestRegisterOrLogin
    ): Response<UserRespose>

    @GET("wishes")
    suspend fun   getWishList(): retrofit2.Response<List<Wish>>

    @POST("wishes")
    suspend fun addWish(
        @Body addWish: RequestAddWish
    ): Response<ResponseMessage>

    @PATCH("wishes")
    suspend fun updateWish(
        @Body updateWish: RequestUpdateWish
    ): Response<ResponseMessage>

    @HTTP(method = "DELETE", path = "wishes", hasBody = true)
    suspend fun deleteWish(
        @Body deleteWish: RequestDeleteWish
    ): Response<ResponseMessage>

}