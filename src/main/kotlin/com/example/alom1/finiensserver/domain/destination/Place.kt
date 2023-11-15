package com.example.alom1.finiensserver.domain.destination

import com.example.alom1.finiensserver.domain.core.Coordinate

open class Place(
    placeName:String,
    placeAddress: String,
    coordinate: Coordinate,
    distance: Double
) {
    var placeName: String = placeName
        protected set
    var placeAddress: String = placeAddress
        protected set
    var coordinate: Coordinate = coordinate
        protected set

    var distance: Double = distance
        protected set
}