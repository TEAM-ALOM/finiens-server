package com.example.alom1.finiensserver.station.domain

import com.example.alom1.finiensserver.core.PrimaryKeyStaticEntity
import com.example.alom1.finiensserver.core.Coordinate
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "subway_station")
class Station(
    name: String,
    coordinate: Coordinate,
): PrimaryKeyStaticEntity() {
    @Column(name = "station_name", nullable = false)
    var name: String = name
        protected set

    @Embedded
    var coordinate: Coordinate = coordinate
        protected set
}