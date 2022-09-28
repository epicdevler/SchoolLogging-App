package com.cedarsstudio.internal.schoollogging.utils

data class UiState<D>(
    val state: State = State.Loading,
    val data: D? = null,
    val dataList: MutableList<D> = mutableListOf(),
    val msg: String? = null,
    val route: String? = null
)

enum class State {
    None, Loading, Error, Success
}
