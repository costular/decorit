package com.costular.decorit.presentation.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.costular.decorit.R
import com.costular.decorit.databinding.FragmentFilterBinding
import com.costular.decorit.presentation.search.SearchState
import com.costular.decorit.presentation.search.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class FilterFragment : BottomSheetDialogFragment() {

    private val viewModel: SearchViewModel by viewModels()

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
        listenState()
        listenEvents()
        listenActions()
    }

    private fun listenActions() {

    }

    private fun listenEvents() {

    }

    private fun listenState() {
        onStates(viewModel) { state ->
            if (state is SearchState) handleState(state)
        }
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

            val colors = state.filterColors.map { color ->
                ColorSelectorModel(color.color, color.isSelected) { photoColor ->
                    viewModel.selectColor(photoColor)
                }.id(color.color.value.name)
            }

            ColorsCarouselModel_()
                .id("colors")
                .numViewsToShowOnScreen(6f)
                .models(colors)
                .addTo(this)
        }
    }

}