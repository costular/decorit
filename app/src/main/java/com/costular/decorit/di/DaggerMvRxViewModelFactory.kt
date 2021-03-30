package com.costular.decorit.di

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.*
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent

/**
 * A [MvRxViewModelFactory] which makes it easy to create instances of a ViewModel
 * using its AssistedInject Factory. This class should be implemented by the companion object
 * of every ViewModel which uses AssistedInject.
 *
 * @param viewModelClass The [Class] of the ViewModel being requested for creation
 *
 * This class accesses the map of [AssistedViewModelFactory]s from [ApplicationComponent] via an [EntryPoint]
 * and uses it to retrieve the requested ViewModel's factory class. It then creates an instance of this ViewModel
 * using the retrieved factory and returns it.
 *
 * Example:
 *
 * class MyViewModel @AssistedInject constructor(...): BaseViewModel<MyState>(...) {
 *
 *   @AssistedInject.Factory
 *   interface Factory : AssistedViewModelFactory<MyViewModel, MyState> {
 *     ...
 *   }
 *
 *   companion object : DaggerMvRxViewModelFactory<MyViewModel, MyState>(MyViewModel::class.java)
 *
 * }
 */
abstract class DaggerMvRxViewModelFactory<VM : MviViewModel<S>, S : MavericksState>(
    private val viewModelClass: Class<out MviViewModel<S>>
) : MavericksViewModelFactory<VM, S> {

    override fun create(viewModelContext: ViewModelContext, state: S): VM? {
        return createViewModel(viewModelContext.activity, state)
    }

    private fun <VM : MviViewModel<S>, S : MavericksState> createViewModel(
        fragmentActivity: FragmentActivity,
        state: S
    ): VM {
        val viewModelFactoryMap = EntryPoints.get(
            fragmentActivity.applicationContext, DaggerMvrxViewModelFactoryEntryPoint::class.java
        ).viewModelFactories
        val viewModelFactory = viewModelFactoryMap[viewModelClass]

        @Suppress("UNCHECKED_CAST")
        val castedViewModelFactory = viewModelFactory as? AssistedViewModelFactory<VM, S>
        val viewModel = castedViewModelFactory?.create(state)
        return viewModel as VM
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerMvrxViewModelFactoryEntryPoint {
    val viewModelFactories: Map<Class<out MviViewModel<*>>, AssistedViewModelFactory<*, *>>
}