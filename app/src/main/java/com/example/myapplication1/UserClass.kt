package com.example.myapplication1

import java.io.Serializable

data class UserClass(
    val points: String = "",
    val name: String = "",
    val email: String = "",
    val isFriend : Boolean = false
) : Serializable