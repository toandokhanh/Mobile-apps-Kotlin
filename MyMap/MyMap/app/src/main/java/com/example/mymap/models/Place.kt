package com.example.mymap.Models

import java.io.Serializable

data class Place(
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
): Serializable  // de co thé truyén dit liéu class nay qua Intent
