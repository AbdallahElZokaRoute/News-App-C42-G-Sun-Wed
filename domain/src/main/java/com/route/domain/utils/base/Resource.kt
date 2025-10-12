package com.route.domain.utils.base

sealed interface Resource<T> {
    class Initial<T> : Resource<T>
    class Loading<T> : Resource<T>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val errorMessage: String) : Resource<T>
}
