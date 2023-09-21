package com.nameisjayant.mobileassesment.features.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.mobileassesment.features.ui.viewmodel.states.ResponseStates
import com.nameisjayant.mobileassesment.base.convertErrorJsonToObject
import com.nameisjayant.mobileassesment.base.doOnFailure
import com.nameisjayant.mobileassesment.base.doOnLoading
import com.nameisjayant.mobileassesment.base.doOnSuccess
import com.nameisjayant.mobileassesment.data.models.ResponseModel
import com.nameisjayant.mobileassesment.data.repository.MainRepository
import com.nameisjayant.mobileassesment.utils.EMPTY_JSON
import com.nameisjayant.mobileassesment.utils.SOMETHING_WENT_WRONG
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val moshi: Moshi
) : ViewModel() {

    /*
    store the states in it and observed in the composable function
     */
    private val _responseEventFlow: MutableStateFlow<ResponseStates<ResponseModel>> =
        MutableStateFlow(ResponseStates())
    var responseEventFlow = _responseEventFlow.asStateFlow()
        private set

    /*
    fired this event when screen is visible for the first time
     */

    fun onEvent() = viewModelScope.launch {
        repository.getData()
            .doOnLoading {
                _responseEventFlow.value = ResponseStates(
                    isLoading = true
                )
            }.doOnFailure {
                val error = convertErrorJsonToObject(moshi, it ?: EMPTY_JSON)
                _responseEventFlow.value = ResponseStates(
                    error = error?.message ?: SOMETHING_WENT_WRONG
                )
            }.doOnSuccess {
                _responseEventFlow.value = ResponseStates(
                    data = it
                )
            }.collect()
    }

}