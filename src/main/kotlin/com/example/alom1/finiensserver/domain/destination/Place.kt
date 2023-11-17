package com.example.alom1.finiensserver.domain.destination

import com.example.alom1.finiensserver.domain.core.Coordinate
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class Place(
    val placeName: String,
    val placeAddress: String,
    val coordinate: Coordinate,
    val distance: Double
) {
    companion object {
        fun fromDto(placeName:String, placeAddress:String, latitude:Double, longitude:Double, distance: Double) : Place {
            return Place(
                placeName=placeName,
                placeAddress=placeAddress,
                coordinate = Coordinate(latitude=latitude, longitude = longitude),
                distance=distance
            )
        }
    }
}
