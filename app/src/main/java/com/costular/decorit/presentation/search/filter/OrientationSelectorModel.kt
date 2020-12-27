package com.costular.decorit.presentation.search.filter

import android.view.View
import com.costular.decorit.R
import com.costular.decorit.domain.model.PhotoOrientation
import com.costular.decorit.util.recycler.KotlinModel
import com.google.android.material.button.MaterialButtonToggleGroup

class OrientationSelectorModel(
    private val selected: PhotoOrientation,
    private val selectListener: (selected: PhotoOrientation) -> Unit
) : KotlinModel(R.layout.item_orientation_selector) {

    private val buttonToggleOrientation by bind<MaterialButtonToggleGroup>(R.id.buttonToggleOrientation)

    override fun bind() {
        buttonToggleOrientation.removeOnButtonCheckedListener(listener)
        buttonToggleOrientation.check(orientationToButtonId(selected))
        buttonToggleOrientation.addOnButtonCheckedListener(listener)
    }

    override fun unbind(view: View) {
        buttonToggleOrientation.removeOnButtonCheckedListener(listener)
        super.unbind(view)
    }

    private val listener = MaterialButtonToggleGroup.OnButtonCheckedListener { _, _, _ ->
        selectListener.invoke(buttonIdToPhotoOrientation(buttonToggleOrientation.checkedButtonId))
    }

    private fun orientationToButtonId(photoOrientation: PhotoOrientation): Int =
        when (photoOrientation) {
            PhotoOrientation.VERTICAL -> R.id.buttonPortrait
            PhotoOrientation.HORIZONTAL -> R.id.buttonLandscape
            PhotoOrientation.ANY -> R.id.buttonAny
        }

    private fun buttonIdToPhotoOrientation(buttonId: Int): PhotoOrientation =
        when (buttonId) {
            R.id.buttonPortrait -> PhotoOrientation.VERTICAL
            R.id.buttonLandscape -> PhotoOrientation.HORIZONTAL
            R.id.buttonAny -> PhotoOrientation.ANY
            else -> PhotoOrientation.ANY
        }

}