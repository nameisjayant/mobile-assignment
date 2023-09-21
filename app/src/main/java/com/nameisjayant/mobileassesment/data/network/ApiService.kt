package com.nameisjayant.mobileassesment.data.network

import com.nameisjayant.mobileassesment.data.models.ResponseModel
import com.nameisjayant.mobileassesment.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getData() : Response<ResponseModel>

}