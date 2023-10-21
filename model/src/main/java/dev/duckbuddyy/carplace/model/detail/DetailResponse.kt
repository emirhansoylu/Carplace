package dev.duckbuddyy.carplace.model.detail

import android.os.Build
import android.text.Html
import dev.duckbuddyy.carplace.model.Category
import dev.duckbuddyy.carplace.model.Location
import dev.duckbuddyy.carplace.model.Property
import dev.duckbuddyy.carplace.model.UserInfo
import dev.duckbuddyy.carplace.model.enums.PhotoSize
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
) {
    fun getSizedPhotos(photoSize: PhotoSize = PhotoSize.Large) = photos.map { photoUrl: String ->
        photoUrl.replace("{0}", photoSize.resolution)
    }

    val actualPrice: String get() = priceFormatted ?: price.toString()

    val escapedText: String?
        get() = text?.let {
            if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(it)
            }
        }?.toString()
}