package com.costular.decorit.data.unsplash.model

import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer

fun UnsplashUserDTO.toPhotographer(): Photographer =
    Photographer(
        this.id,
        this.name,
        this.profileImageDTO.medium
    )

fun UnsplashPhotoDTO.toPhoto(): Photo =
    Photo(
        "${UNSPLASH}-$id",
        this.width,
        this.height,
        UNSPLASH,
        this.userDTO.toPhotographer(),
        false,
        this.urlsDTO.original,
        this.urlsDTO.full,
        this.urlsDTO.regular,
        this.urlsDTO.small
    )