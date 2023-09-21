package com.nameisjayant.mobileassesment.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.net.UnknownHostException

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {
    emit(ApiState.Loading)
    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
            }
        } else {
            when (response.code()) {
                in 500..509 -> emit(ApiState.Failure("{\"message\" : \"INTERNAL SERVER ERROR\"}"))
                413 -> emit(ApiState.Failure("{\"message\" : \"FILE IS TOO LARGE\"}"))
                else -> response.errorBody()?.let { error ->
                    error.close()
                    emit(ApiState.Failure(error.string()))
                }

            }
        }
    } catch (e: UnknownHostException) {
        emit(ApiState.Failure("{\"message\" : \"INTERNET NOT FOUND\"}"))
    }catch (e: Exception) {
        emit(ApiState.Failure("{\"message\" : \"${e.message}\"}"))
    }

}.flowOn(Dispatchers.IO)

