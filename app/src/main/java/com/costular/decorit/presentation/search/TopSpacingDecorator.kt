package com.costular.decorit.presentation.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class TopSpacingDecorator(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (val layout = parent.layoutManager) {
            is StaggeredGridLayoutManager -> {
                if (isFirstRow(layout, parent.getChildAdapterPosition(view))) {
                    outRect.set(0, space, 0, 0)
                }
            }
        }
    }

    private fun isFirstRow(layout: StaggeredGridLayoutManager, position: Int): Boolean =
        position < layout.spanCount

}