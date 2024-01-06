package com.example.androidtest.utils

import androidx.compose.runtime.Composable
import com.example.androidtest.view.state.State
import com.example.androidtest.view.viewmodel.BaseViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun <S : State, VM : BaseViewModel<S>> VM.collectState() = state.collectAsStateWithLifecycle()
