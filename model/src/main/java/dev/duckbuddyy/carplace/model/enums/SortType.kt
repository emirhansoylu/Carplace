package dev.duckbuddyy.carplace.model.enums

sealed class SortType(val sortType: String) {
    data object Price : SortType("0")
    data object Date : SortType("1")
    data object Year : SortType("2")

    companion object {
        fun fromDirectionId(id: String): SortType = when (id) {
            "0" -> Price
            "1" -> Date
            "2" -> Year
            else -> throw IllegalArgumentException()
        }
    }
}
