package com.costular.decorit.presentation.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.costular.decorit.R
import com.costular.decorit.databinding.FragmentPhotosBinding
import com.costular.decorit.presentation.common.PhotoEpoxy
import com.costular.decorit.util.extensions.viewBinding
import com.costular.decorit.util.recycler.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onStates

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos) {

    val viewModel: PhotosViewModel by viewModels()

    val binding by viewBinding(FragmentPhotosBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listen()
        viewModel.load()
    }

    private fun init() {
        binding.epoxyPhotos.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }
            itemAnimator = DefaultItemAnimator()

            val space = resources.getDimensionPixelSize(R.dimen.space_between_photos)
            addItemDecoration(SpaceItemDecoration(space))
        }

    }

    private fun listen() {
        listenActions()
        listenState()
        listenEvents()
    }

    private fun listenEvents() {

    }

    private fun listenState() {
        onStates(viewModel) { state ->
            when (state) {
                is PhotosState -> handleState(state)
            }
        }
    }

    private fun handleState(state: PhotosState) {
        binding.epoxyPhotos.withModels {
            state.photos.forEach { photo ->

                PhotoEpoxy(photo.medium)
                    .id(photo.id.id)
                    .addTo(this)
            }
        }
    }

    private fun listenActions() {
    }


}