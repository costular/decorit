package com.costular.decorit.data.unsplash.model

import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.PhotographerId
import com.costular.decorit.util.SourceId

fun UnsplashUserDTO.toPhotographer(): Photographer =
    Photographer(
        PhotographerId(this.id),
        this.name,
        this.profileImageDTO.medium
    )

fun UnsplashPhotoDTO.toPhoto(): Photo =
    Photo(
        PhotoId(this.id),
        this.width,
        this.height,
        SourceId(UNSPLASH),
        this.userDTO.toPhotographer(),
        false,
        this.urlsDTO.original,
        this.urlsDTO.full,
        this.urlsDTO.regular,
        this.urlsDTO.small
    )