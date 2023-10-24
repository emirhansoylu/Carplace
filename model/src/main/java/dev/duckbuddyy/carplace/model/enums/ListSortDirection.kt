package dev.duckbuddyy.carplace.model.enums

sealed class ListSortDirection(val direction: String) {
    data object Ascending : ListSortDirection("0")
    data object Descending : ListSortDirection("1")

    companion object {
        fun fromDirectionId(id: String): ListSortDirection = when (id) {
            "0" -> Ascending
            "1" -> Descending
            else -> throw IllegalArgumentException()
        }
    }
}
