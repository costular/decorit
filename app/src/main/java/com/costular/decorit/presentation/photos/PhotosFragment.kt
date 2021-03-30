package com.costular.decorit.presentation.photos

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.costular.decorit.R
import com.costular.decorit.databinding.FragmentPhotosBinding
import com.costular.decorit.presentation.common.PhotoModel
import com.costular.decorit.util.extensions.viewBinding
import com.costular.decorit.util.recycler.EndlessRecyclerViewScrollListener
import com.costular.decorit.util.recycler.LoadingEpoxy_
import com.costular.decorit.util.recycler.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos), MavericksView {

    val viewModel: PhotosViewModel by fragmentViewModel()

    val binding by viewBinding(FragmentPhotosBinding::bind)

    lateinit var paginationListener: EndlessRecyclerViewScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listen()
        viewModel.load()
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
            addItemDecoration(SpaceItemDecoration(space))

            paginationListener = object : EndlessRecyclerViewScrollListener(layoutMan) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.load()
                }
            }
            addOnScrollListener(paginationListener)
        }
    }

    private fun listen() {
        listenActions()
        listenEvents()
    }

    private fun listenEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvents.asLiveData().observe(viewLifecycleOwner) { event ->
                when (event) {
                    is PhotosEvents.OpenPhoto -> {
                        val directions =
                            PhotosFragmentDirections.actionNavPhotosToPhotoDetailActivity(event.photo)
                        findNavController().navigate(directions)
                    }
                }
            }
        }
    }

    private fun handleState(state: PhotosState) {
        binding.epoxyPhotos.withModels {
            spanCount = 2

            state.photos.forEach { photo ->
                PhotoModel(photo.medium) {
                    viewModel.openPhoto(photo)
                }.id(photo.id).addTo(this)
            }

            LoadingEpoxy_()
                .id("loading")
                .addIf(state.loadingMore, this)
        }
    }

    private fun listenActions() {
    }

    override fun invalidate() {
        withState(viewModel) { state -> handleState(state) }
    }

}