package dev.duckbuddyy.carplace.model.listing

import dev.duckbuddyy.carplace.model.Category
import dev.duckbuddyy.carplace.model.Location
import dev.duckbuddyy.carplace.model.Property
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingResponseItem(
    @SerialName("category")
    val category: Category,
    @SerialName("date")
    val date: String,
    @SerialName("dateFormatted")
    val dateFormatted: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("location")
    val location: Location,
    @SerialName("modelName")
    val modelName: String?,
    @SerialName("photo")
    val photo: String?,
    @SerialName("price")
    val price: Int,
    @SerialName("priceFormatted")
    val priceFormatted: String?,
    @SerialName("properties")
    val properties: List<Property>,
    @SerialName("title")
    val title: String?
)