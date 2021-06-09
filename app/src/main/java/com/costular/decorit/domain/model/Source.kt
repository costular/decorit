package com.costular.decorit.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String,
    val link: String,
    val iconDrawable: Int
) : Parcelable