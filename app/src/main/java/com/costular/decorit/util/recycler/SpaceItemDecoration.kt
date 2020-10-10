package com.costular.decorit.util.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(space: Int) : ItemDecoration() {

    private val halfSpace = space / 2

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.paddingLeft != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.clipToPadding = false;
        }

        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}