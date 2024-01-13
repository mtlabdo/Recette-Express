package com.example.androidtest.view.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Base for all the ViewModels
 */
abstract class BaseViewModel<VIEW_STATE : Any> : ViewModel() {


    protected abstract val initialViewState: VIEW_STATE
    private val mutableViewState by mutableStateFlow { initialViewState }
    val viewState by immutableFlow { mutableViewState }

    protected fun updateViewState(newState: VIEW_STATE) {
        MainScope().launch {
            mutableViewState.emit(newState)
        }
    }

    private fun <T> mutableStateFlow(initialValueProvider: () -> T) =
        lazy { MutableStateFlow(initialValueProvider()) }

    private fun <T, FLOW : MutableSharedFlow<T>> immutableFlow(
        initializer: () -> FLOW
    ): Lazy<Flow<T>> = lazy { initializer() }

}
