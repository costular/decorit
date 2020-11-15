package com.costular.decorit.presentation.common

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.costular.decorit.R
import com.costular.decorit.databinding.ItemPhotoBinding
import com.costular.decorit.util.recycler.ViewBindingKotlinModel

data class PhotoModel(
    val photoUrl: String,
    val onClickListener: () -> Unit
) : ViewBindingKotlinModel<ItemPhotoBinding>(R.layout.item_photo) {

    override fun ItemPhotoBinding.bind() {
        Glide.with(this.imagePhoto)
            .load(photoUrl)
            .placeholder(R.color.placeholder)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this.imagePhoto)

        imagePhoto.setOnClickListener { onClickListener() }
    }

}