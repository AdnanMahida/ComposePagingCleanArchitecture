package com.ad.composepagingcleanarchitecture.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BEntity(
    @PrimaryKey
    val id:Int,
    val name:String,
    val tagline:String,
    val description :String,
    val firstBrewed:String,
    val imageUrl:String?,
)
