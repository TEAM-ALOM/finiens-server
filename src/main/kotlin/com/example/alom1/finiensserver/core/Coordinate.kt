package com.example.alom1.finiensserver.core

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Coordinate(
    @Column(name = "latitude", nullable = false)
    val latitude: Double,

    @Column(name = "longitude", nullable = false)
    val longitude: Double
)