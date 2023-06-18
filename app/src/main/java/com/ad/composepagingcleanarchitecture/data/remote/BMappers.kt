package com.ad.composepagingcleanarchitecture.data.remote

import com.ad.composepagingcleanarchitecture.data.local.BEntity
import com.ad.composepagingcleanarchitecture.domain.B

fun BDto.toBEntity(): BEntity {
    return BEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BEntity.toB(): B {
    return B(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl
    )

}