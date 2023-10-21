package dev.duckbuddyy.carplace.detail

import dev.duckbuddyy.carplace.model.detail.DetailResponse

sealed class DetailState {
    data class Success(val detail: DetailResponse) : DetailState()
    data object Loading : DetailState()
    data object Error : DetailState()
}
