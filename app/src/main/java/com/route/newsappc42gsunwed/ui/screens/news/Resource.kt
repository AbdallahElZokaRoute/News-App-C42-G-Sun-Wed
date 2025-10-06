package com.route.newsappc42gsunwed.ui.screens.news

// sealed class   ,    sealed interface

sealed class SessionsDays(val content: String) {
//    class Sunday() : SessionsDays()
//    class Wednesday() : SessionsDays()
}

sealed interface Weekdays {
    class Saturday : Weekdays
}

sealed interface Resource<T> {
    class Initial<T> : Resource<T>
    class Loading<T> : Resource<T>
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val errorMessage: String) : Resource<T>
}


