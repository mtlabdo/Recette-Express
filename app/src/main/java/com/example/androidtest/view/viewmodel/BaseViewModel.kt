package com.example.androidtest.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidtest.view.state.State
import kotlinx.coroutines.flow.StateFlow

/**
 * Base for all the ViewModels
 */
abstract class BaseViewModel<STATE : State> : ViewModel() {
    /**
     * State to be exposed to the UI layer
     */
    abstract val state: StateFlow<STATE>

    /**
     * Retrieves the current UI state
     */
    val currentState: STATE get() = state.value
}
