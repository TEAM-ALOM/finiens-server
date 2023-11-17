package com.example.alom1.finiensserver.data.destination

import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.destination.Place
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class DestinationResponseDto(
    @get:JsonProperty("place_name")
    val placeName: String,
    @get:JsonProperty("address_name")
    val placeAddress: String,
    @get:JsonProperty("x")
    val longitude: Double,
    @get:JsonProperty("y")
    val latitude: Double,
    @get:JsonProperty("distance")
    val distance: Double
) {
//    companion object {
        fun toEntity() : Place {
            return Place(
                placeName=this.placeName,
                placeAddress=this.placeAddress,
                coordinate = Coordinate(latitude=this.latitude, longitude = this.longitude),
                distance=this.distance
            )
        }
//    }
}