package com.costular.decorit.presentation.search.filter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.costular.decorit.R
import com.costular.decorit.databinding.FragmentFilterBinding
import com.costular.decorit.domain.model.ColorValue
import com.costular.decorit.domain.model.PhotoColor
import com.costular.decorit.presentation.search.SearchEvents
import com.costular.decorit.presentation.search.SearchState
import com.costular.decorit.presentation.search.SearchViewModel
import com.costular.decorit.util.extensions.getAttrColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates
import io.uniflow.android.flow.onTakeEvents

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment(), MavericksView {

    private val viewModel: SearchViewModel by fragmentViewModel()

    private lateinit var binding: FragmentFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        binding = FragmentFilterBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listen()
    }

    private fun init() {
        with(binding.epoxyFilters) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun listen() {
        listenEvents()
        listenActions()
    }

    private fun listenActions() {
        binding.buttonSearchResults.setOnClickListener {
            dismiss()
        }
    }

    private fun listenEvents() {

    }

    private fun handleState(state: SearchState) {
        binding.epoxyFilters.withModels {

            FilterHeaderModel(R.string.search_filter_orientation_header)
                .id("orientation-header")
                .addTo(this)

            OrientationSelectorModel(state.params.orientation) { orientation ->
                viewModel.selectOrientation(orientation)
            }.id("orientation-selector").addTo(this)

            FilterHeaderModel(R.string.search_filter_color_header)
                .id("color-header")
                .addTo(this)

            val colors = state.filterColors
                .map { color ->
                    ColorSelectorModel(
                        mapColorValueToPhotoColor(color.color),
                        color.isSelected
                    ) { photoColor ->
                        viewModel.selectColor(photoColor)
                    }.id(color.color.name)
                }

            ColorsCarouselModel_()
                .id("colors")
                .numViewsToShowOnScreen(6f)
                .models(colors)
                .addTo(this)
        }
    }

    private fun mapColorValueToPhotoColor(color: ColorValue): PhotoColor = when (color) {
        ColorValue.WHITE -> PhotoColor(Color.WHITE, ColorValue.WHITE)
        ColorValue.BLACK -> PhotoColor(Color.BLACK, ColorValue.BLACK)
        ColorValue.BLACK_AND_WHITE -> PhotoColor(Color.GRAY, ColorValue.BLACK_AND_WHITE)
        ColorValue.BLUE -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterBlue),
            ColorValue.BLUE
        )
        ColorValue.RED -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterRed),
            ColorValue.RED
        )
        ColorValue.GREEN -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterGreen),
            ColorValue.GREEN
        )
        ColorValue.MAGENTA -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterMagenta),
            ColorValue.MAGENTA
        )
        ColorValue.ORANGE -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterOrange),
            ColorValue.ORANGE
        )
        ColorValue.PURPLE -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterPurple),
            ColorValue.PURPLE
        )
        ColorValue.TEAL -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterTeal),
            ColorValue.TEAL
        )
        ColorValue.YELLOW -> PhotoColor(
            requireContext().getAttrColor(R.attr.colorFilterYellow),
            ColorValue.YELLOW
        )
    }

    override fun invalidate() {
        withState(viewModel) { state -> handleState(state) }
    }

}