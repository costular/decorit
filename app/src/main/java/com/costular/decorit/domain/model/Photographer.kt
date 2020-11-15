package com.costular.decorit.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photographer(
    val id: String,
    val name: String,
    val avatar: String?
) : Parcelable