package com.example.myapplication1

import java.io.Serializable

data class UserClass(
    val email: String = "",
    val Nickname: String = "",
    val isFriend : Boolean = false
) : Serializable