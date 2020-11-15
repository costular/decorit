package com.costular.decorit.util.recycler

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.costular.decorit.R
import com.costular.decorit.databinding.ItemPhotoBinding
import com.google.android.material.progressindicator.ProgressIndicator

@EpoxyModelClass(layout = R.layout.item_loading)
abstract class LoadingEpoxy : EpoxyModelWithHolder<LoadingEpoxy.Holder>() {

    class Holder : KotlinEpoxyHolder() {
        val progress by bind<ProgressIndicator>(R.id.progress)
    }

}