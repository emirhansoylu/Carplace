package dev.duckbuddyy.carplace.model.detail

import dev.duckbuddyy.carplace.model.Category
import dev.duckbuddyy.carplace.model.Location
import dev.duckbuddyy.carplace.model.Property
import dev.duckbuddyy.carplace.model.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse(
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
    @SerialName("photos")
    val photos: List<String>,
    @SerialName("price")
    val price: Int,
    @SerialName("priceFormatted")
    val priceFormatted: String?,
    @SerialName("properties")
    val properties: List<Property>,
    @SerialName("text")
    val text: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("userInfo")
    val userInfo: UserInfo
)