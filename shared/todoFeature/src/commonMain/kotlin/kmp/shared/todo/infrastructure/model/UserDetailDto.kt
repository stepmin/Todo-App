package kmp.shared.todo.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("address")
    val address: AddressDto,
    @SerialName("phone")
    val phone: String,
    @SerialName("website")
    val website: String,
    @SerialName("company")
    val company: CompanyDto,
)

@Serializable
data class AddressDto(
    @SerialName("street")
    val street: String,
    @SerialName("suite")
    val suite: String,
    @SerialName("city")
    val city: String,
    @SerialName("zipcode")
    val zipcode: String,
    @SerialName("geo")
    val geo: GeoDto,
)

@Serializable
data class GeoDto(
    @SerialName("lat")
    val lat: String,
    @SerialName("lng")
    val lng: String,
)

@Serializable
data class CompanyDto(
    @SerialName("name")
    val name: String,
    @SerialName("catchPhrase")
    val catchPhrase: String,
    @SerialName("bs")
    val bs: String,
)