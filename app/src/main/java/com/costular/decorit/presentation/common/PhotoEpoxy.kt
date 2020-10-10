package com.costular.decorit.presentation.common

import com.bumptech.glide.Glide
import com.costular.decorit.R
import com.costular.decorit.databinding.ItemPhotoBinding
import com.costular.decorit.util.recycler.ViewBindingKotlinModel

data class PhotoEpoxy(
    val photoUrl: String
) : ViewBindingKotlinModel<ItemPhotoBinding>(R.layout.item_photo) {

    override fun ItemPhotoBinding.bind() {
        Glide.with(this.imagePhoto)
            .load(photoUrl)
            .placeholder(R.color.placeholder)
            .dontTransform()
            .into(this.imagePhoto)
    }

}