package com.example.b2012046_appchiasedieuuoc.Models

import com.google.gson.annotations.SerializedName

data class RequestUpdateWish(

    val idUser: String,
    val idWish: String,
    @SerializedName("name")
    val fullName: String,
    val content: String
)
