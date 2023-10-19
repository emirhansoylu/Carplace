package dev.duckbuddyy.carplace.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("id")
    val id: Int,
    @SerialName("nameSurname")
    val nameSurname: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("phoneFormatted")
    val phoneFormatted: String
)