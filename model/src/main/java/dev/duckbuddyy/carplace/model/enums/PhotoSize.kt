package dev.duckbuddyy.carplace.model.enums

sealed class PhotoSize(val resolution: String) {
    data object XSmall : PhotoSize("160x120")
    data object Small : PhotoSize("240x180")
    data object Medium : PhotoSize("580x435")
    data object Large : PhotoSize("800x600")
    data object XLarge : PhotoSize("1920x1080")
}
