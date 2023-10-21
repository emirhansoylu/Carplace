package dev.duckbuddyy.carplace.model.enums

sealed class ListSortDirection(val direction: String) {
    data object Ascending : ListSortDirection("0")
    data object Descending : ListSortDirection("1")
}
