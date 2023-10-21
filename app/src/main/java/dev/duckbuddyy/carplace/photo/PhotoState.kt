package dev.duckbuddyy.carplace.photo

sealed class PhotoState {
    data class Success(val images: Array<String>) : PhotoState()
    data object Loading : PhotoState()
    data object Error : PhotoState()
}
