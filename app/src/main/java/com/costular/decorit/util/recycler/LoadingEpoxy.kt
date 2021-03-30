package com.costular.decorit.util.recycler

import android.widget.FrameLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.costular.decorit.R
import com.costular.decorit.databinding.ItemPhotoBinding
import com.google.android.material.progressindicator.ProgressIndicator

@EpoxyModelClass(layout = R.layout.item_loading)
abstract class LoadingEpoxy : EpoxyModelWithHolder<LoadingEpoxy.Holder>() {

    override fun bind(holder: Holder) {
        super.bind(holder)
        val layoutParams = holder.root.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = true
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val root by bind<FrameLayout>(R.id.root)
        val progress by bind<ProgressIndicator>(R.id.progress)
    }

}