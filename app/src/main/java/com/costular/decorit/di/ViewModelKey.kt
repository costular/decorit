package com.costular.decorit.di

import com.costular.decorit.presentation.base.MviViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * A [MapKey] for populating a map of ViewModels and their factories.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out MviViewModel<*>>)