package com.ad.composepagingcleanarchitecture.data.remote

data class BDto(
    val id:Int,
    val name:String,
    val tagline:String,
    val description :String,
    val first_brewed:String,
    val image_url:String?,
)