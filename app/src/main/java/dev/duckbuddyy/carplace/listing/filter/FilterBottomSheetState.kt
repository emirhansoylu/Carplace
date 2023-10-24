package dev.duckbuddyy.carplace.listing.filter

import android.os.Bundle
import androidx.core.os.bundleOf
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType

data class FilterBottomSheetState(
    val minDate: String? = null,
    val maxDate: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val sortType: SortType? = null,
    val sortDirection: ListSortDirection? = null
) {
    fun toBundle() = bundleOf(
        KEY_MIN_DATE to minDate,
        KEY_MAX_DATE to maxDate,
        KEY_MIN_YEAR to minYear,
        KEY_MAX_YEAR to maxYear,
        KEY_SORT_TYPE to sortType?.sortType,
        KEY_SORT_DIRECTION to sortDirection?.direction
    )

    internal companion object {
        private const val KEY_MIN_DATE = "KEY_MIN_DATE"
        private const val KEY_MAX_DATE = "KEY_MAX_DATE"
        private const val KEY_MIN_YEAR = "KEY_MIN_YEAR"
        private const val KEY_MAX_YEAR = "KEY_MAX_YEAR"
        private const val KEY_SORT_TYPE = "KEY_SORT_TYPE"
        private const val KEY_SORT_DIRECTION = "KEY_SORT_DIRECTION"

        fun fromBundle(bundle: Bundle) = FilterBottomSheetState(
            minDate = bundle.getString(KEY_MIN_DATE),
            maxDate = bundle.getString(KEY_MAX_DATE),
            minYear = bundle.getInt(KEY_MIN_YEAR),
            maxYear = bundle.getInt(KEY_MAX_YEAR),
            sortType = bundle.getString(KEY_SORT_TYPE)?.let { SortType.fromDirectionId(it) },
            sortDirection = bundle.getString(KEY_SORT_DIRECTION)?.let { ListSortDirection.fromDirectionId(it) },
        )
    }
}
