package dev.duckbuddyy.carplace.photo

sealed class PhotoState {
    data class Success(
        val images: Array<String>,
        val imagePosition: Int
    ) : PhotoState() {
        val currentIndex get() = "${imagePosition + 1} / ${images.size}"
    }

    data object Loading : PhotoState()
    data object Error : PhotoState()
}
