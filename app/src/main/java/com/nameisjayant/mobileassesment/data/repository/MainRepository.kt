package com.nameisjayant.mobileassesment.data.repository


import com.nameisjayant.mobileassesment.data.models.ResponseModel
import com.nameisjayant.mobileassesment.data.network.ApiService
import com.nameisjayant.mobileassesment.base.ApiState
import com.nameisjayant.mobileassesment.base.toResultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
This repository will handle all the network calls and emit to viewModel
 */
class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getData() : Flow<ApiState<ResponseModel>> = toResultFlow {
        apiService.getData()
    }

}