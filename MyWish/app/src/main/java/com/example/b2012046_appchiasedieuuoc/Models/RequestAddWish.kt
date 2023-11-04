package com.example.b2012046_appchiasedieuuoc.Models

import com.google.gson.annotations.SerializedName

data class RequestAddWish(
    val idUser: String,

    @SerializedName("name")
    val fullname: String,
    val content: String


)
