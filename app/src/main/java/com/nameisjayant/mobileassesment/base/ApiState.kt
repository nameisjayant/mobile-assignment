package com.nameisjayant.mobileassesment.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/*
This will handle the api state
 */
sealed class ApiState<out T> {

    data class Success<out R>(val data: R) : ApiState<R>()
    data class Failure(val error: String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()

}

/*
These are for converting  Success , Failure and Loading of Flow<ApiState<T>> to T in viewModel
 */
fun <T> Flow<ApiState<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

fun <T> Flow<ApiState<T>>.doOnFailure(action: suspend (String?) -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Failure) {
            action(result.error)
        }
        return@transform emit(result)
    }

fun <T> Flow<ApiState<T>>.doOnLoading(action: suspend () -> Unit): Flow<ApiState<T>> =
    transform { result ->
        if (result is ApiState.Loading) {
            action()
        }
        return@transform emit(result)
    }