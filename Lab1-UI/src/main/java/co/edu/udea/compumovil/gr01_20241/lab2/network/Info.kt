package co.edu.udea.compumovil.gr01_20241.lab2.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info (
    @SerialName("detail_placeholder")
    val detailPlaceholder: String,
    @SerialName("ingredients_list")
    val ingredientsList: String
)