package com.example.androidapplication

interface UserClickListener {
    fun onItemClick(user: User)
    fun onMenuDeleteClick(user: User)
    fun onMenuPassChangeClick(user: User)
}