package com.costular.decorit.presentation.search.filter

import com.costular.decorit.R
import com.costular.decorit.domain.model.PhotoColor
import com.costular.decorit.util.recycler.KotlinModel
import com.costular.decorit.util.view.ColorView

data class ColorSelectorModel(
    val color: PhotoColor,
    val isSelected: Boolean,
    val selectedListener: (color: PhotoColor) -> Unit
) : KotlinModel(R.layout.item_color_selector) {

    private val colorView by bind<ColorView>(R.id.colorSelector)

    override fun bind() {
        colorView.circleColor = color.colorInt
        colorView.shadowColor = color.colorInt
        colorView.shadowEnable = true
        colorView.isChecked = isSelected

        colorView.setOnClickListener { selectedListener.invoke(color) }
    }

}