package ua.ilyadreamix.amino.http.core

data class ApiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val code: Int = -1
)