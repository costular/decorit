package com.costular.decorit.data.unsplash.model

import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.data.repository.photos.DefaultSourcesRepository
import com.costular.decorit.di.Unsplash
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer

fun UnsplashUserDTO.toPhotographer(): Photographer =
    Photographer(
        this.id,
        this.name,
        this.profileImageDTO.medium,
        this.links.self
    )

fun UnsplashPhotoDTO.toPhoto(): Photo =
    Photo(
        "${UNSPLASH}-$id",
        this.width,
        this.height,
        DefaultSourcesRepository.Unsplash,
        this.userDTO.toPhotographer(),
        false,
        this.urlsDTO.original,
        this.urlsDTO.full,
        this.urlsDTO.regular,
        this.urlsDTO.small
    )