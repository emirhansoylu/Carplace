package dev.duckbuddyy.carplace.listing.filter

import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType

data class FilterBottomSheetState(
    val minDate: String? = null,
    val maxDate: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val sortType: SortType? = null,
    val sortDirection: ListSortDirection? = null
)
