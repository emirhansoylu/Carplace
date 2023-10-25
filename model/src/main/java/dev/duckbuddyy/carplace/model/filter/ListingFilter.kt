package dev.duckbuddyy.carplace.model.filter

import android.os.Parcel
import android.os.Parcelable
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType

data class ListingFilter(
    val minDate: String? = null,
    val maxDate: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val sortType: SortType? = null,
    val sortDirection: ListSortDirection? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()?.let { SortType.fromDirectionId(it) },
        parcel.readString()?.let { ListSortDirection.fromDirectionId(it) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(minDate)
        parcel.writeString(maxDate)
        parcel.writeValue(minYear)
        parcel.writeValue(maxYear)
        parcel.writeString(sortType?.sortType)
        parcel.writeString(sortDirection?.direction)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListingFilter> {
        override fun createFromParcel(parcel: Parcel): ListingFilter {
            return ListingFilter(parcel)
        }

        override fun newArray(size: Int): Array<ListingFilter?> {
            return arrayOfNulls(size)
        }
    }
}
