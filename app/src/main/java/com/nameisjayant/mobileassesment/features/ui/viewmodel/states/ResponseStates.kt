package com.nameisjayant.mobileassesment.features.ui.viewmodel.states

/*
Generic Response state, it will keep the state whatever server sent
 */

data class ResponseStates<out T>(
    val data: T? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
