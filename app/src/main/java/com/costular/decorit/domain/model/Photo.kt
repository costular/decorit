package com.costular.decorit.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val source: Source,
    val photographer: Photographer,
    val isFavorite: Boolean,
    val original: String,
    val large: String,
    val medium: String,
    val small: String
) : Parcelable {

    val resolution: String = "${width}x${height}"
    val sourcedId: String = id.split("-").last()

    fun photoUrlFromQuality(photoQuality: PhotoQuality): String = when (photoQuality) {
        PhotoQuality.Low -> small
        PhotoQuality.Medium -> medium
        PhotoQuality.Full -> large
        PhotoQuality.High -> original
    }

}