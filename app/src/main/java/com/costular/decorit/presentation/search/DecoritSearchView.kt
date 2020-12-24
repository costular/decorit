package com.costular.decorit.presentation.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.costular.decorit.R
import com.costular.decorit.databinding.DecoritSearchViewBinding
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges
import kotlin.properties.Delegates

class DecoritSearchView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr), LifecycleObserver {

    private var textHint: String? by Delegates.observable(null) { _, _, new ->
        binding.searchSearchEditText.hint = new
    }

    private lateinit var binding: DecoritSearchViewBinding
    lateinit var textChanges: Flow<String>

    init {
        inflate()
        readAttrs(attributeSet)
    }

    private fun readAttrs(attributeSet: AttributeSet?) {
        context.withStyledAttributes(attributeSet, R.styleable.DecoritSearchView) {
            textHint = getString(R.styleable.DecoritSearchView_decoritSearch_hint) ?: ""
        }
    }

    private fun inflate() {
        val view = inflate(context, R.layout.decorit_search_view, this)
        binding = DecoritSearchViewBinding.bind(view)
        setListeners()
    }

    private fun setListeners() {
        textChanges = binding.searchSearchEditText
            .textChanges()
            .onEach { value ->
                if (value.isNotEmpty()) {
                    showClear()
                } else {
                    hideClear()
                }
            }
            .map { it.toString() }

        binding.searchClearText.setOnClickListener { clear() }
    }

    private fun hideClear() {
        TransitionManager.beginDelayedTransition(this)
        binding.searchClearText.visibility = View.GONE
    }

    private fun showClear() {
        TransitionManager.beginDelayedTransition(this)
        binding.searchClearText.visibility = View.VISIBLE
    }

    private fun clear() {
        binding.searchSearchEditText.text = null
    }

}