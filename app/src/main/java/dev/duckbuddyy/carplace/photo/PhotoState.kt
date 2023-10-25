package dev.duckbuddyy.carplace.photo

import dev.duckbuddyy.carplace.model.enums.PhotoSize

sealed class PhotoState {
    data class Success(
        val images: Array<String>,
        val imagePosition: Int
    ) : PhotoState() {
        val currentIndex get() = "${imagePosition + 1} / ${images.size}"

        val actualImages get() = images.map { it.replace("{0}", PhotoSize.XLarge.resolution) }
    }

    data object Loading : PhotoState()
    data object Error : PhotoState()
}
