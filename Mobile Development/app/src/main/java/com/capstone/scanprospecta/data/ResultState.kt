package com.capstone.scanprospecta.data

sealed class ResultState<out R> private constructor() {
    data class success<out T>(val data: T) : ResultState<T>()
    data class error(val error: String) : ResultState<Nothing>()
    object loading : ResultState<Nothing>()
}