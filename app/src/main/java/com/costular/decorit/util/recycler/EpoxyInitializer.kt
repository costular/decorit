package com.costular.decorit.util.recycler

import android.app.Application
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.costular.decorit.util.initializers.AppInitializer
import javax.inject.Inject

class EpoxyInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        // Make EpoxyController diffing async by default
        val asyncHandler = EpoxyAsyncUtil.getAsyncBackgroundHandler()
        EpoxyController.defaultDiffingHandler = asyncHandler
    }
}