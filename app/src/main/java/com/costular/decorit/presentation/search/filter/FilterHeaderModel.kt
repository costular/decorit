package com.costular.decorit.presentation.search.filter

import android.widget.TextView
import androidx.annotation.StringRes
import com.costular.decorit.R
import com.costular.decorit.util.recycler.KotlinModel

class FilterHeaderModel(
    @StringRes val headerRes: Int
) : KotlinModel(R.layout.item_filter_header) {

    private val textTitle by bind<TextView>(R.id.textFilterHeader)

    override fun bind() {
        textTitle.setText(headerRes)
    }

}