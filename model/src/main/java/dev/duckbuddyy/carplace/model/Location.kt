package dev.duckbuddyy.carplace.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("cityName")
    val cityName: String?,
    @SerialName("townName")
    val townName: String?
) {
    override fun toString() = "$townName/$cityName"
}