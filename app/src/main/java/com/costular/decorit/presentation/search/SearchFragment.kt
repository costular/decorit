package com.costular.decorit.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.costular.decorit.R
import com.costular.decorit.databinding.FragmentSearchBinding
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.presentation.common.PhotoModel
import com.costular.decorit.presentation.photos.PhotosFragmentDirections
import com.costular.decorit.util.extensions.getAttrColor
import com.costular.decorit.util.extensions.viewBinding
import com.costular.decorit.util.recycler.EndlessRecyclerViewScrollListener
import com.costular.decorit.util.recycler.LoadingEpoxy_
import com.costular.decorit.util.recycler.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates
import io.uniflow.android.flow.onTakeEvents
import kotlinx.coroutines.flow.*
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MavericksView {

    private val viewModel: SearchViewModel by fragmentViewModel()

    private val binding by viewBinding(FragmentSearchBinding::bind)

    lateinit var paginationListener: EndlessRecyclerViewScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listen()
    }

    private fun init() {
        binding.epoxyPhotos.apply {
            val layoutMan = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
            layoutManager = layoutMan
            itemAnimator = null
            setHasFixedSize(true)

            val space = resources.getDimensionPixelSize(R.dimen.space_between_photos)
            val topSpace = resources.getDimensionPixelSize(R.dimen.search_top_space)
            addItemDecoration(SpaceItemDecoration(space))
            addItemDecoration(TopSpacingDecorator(topSpace))

            paginationListener = object : EndlessRecyclerViewScrollListener(layoutMan) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.search(loadNext = true)
                }
            }
            addOnScrollListener(paginationListener)
        }
    }

    private fun listen() {
        listenEvents()
        listenActions()
    }

    private fun listenActions() {
        binding.fabFilter.setOnClickListener { viewModel.openFilter() }

        binding.materialSearchView.textChanges
            .debounce(300L)
            .catch { Timber.e(it) }
            .onEach { query ->
                viewModel.search(query)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun listenEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvents.asLiveData().observe(viewLifecycleOwner) { event ->
                when (event) {
                    is SearchEvents.OpenFilters -> openFilters()
                    is SearchEvents.OpenPhoto -> openPhoto(event.photo)
                }
            }
        }
    }

    private fun openPhoto(photo: Photo) {
        val directions =
            SearchFragmentDirections.actionNavSearchToPhotoDetailActivity2(photo)
        findNavController().navigate(directions)
    }

    private fun openFilters() {
        val directions = SearchFragmentDirections.actionNavSearchToFilterFragment()
        findNavController().navigate(directions)
    }

    private fun handleState(state: SearchState) {
        binding.fabFilter.visibility = if (state.showFilters) View.VISIBLE else View.GONE

        binding.epoxyPhotos.withModels {
            spanCount = 2

            state.items.forEach { photo ->
                PhotoModel(photo.medium) {
                    viewModel.openPhoto(photo)
                }.id(photo.id).addTo(this)
            }

            LoadingEpoxy_()
                .id("loading")
                .addIf(state.isLoading, this)
        }
    }

    override fun invalidate() {
        withState(viewModel) { state -> handleState(state) }
    }

}