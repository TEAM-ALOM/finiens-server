package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.domain.core.PrimaryKeyStaticEntity
import com.example.alom1.finiensserver.domain.station.Station
import jakarta.persistence.*

@Entity
@Table(name="subway_station_section")
class Section(
    distance: Double,
    duration: Int,
    upStation: Station,
    downStation: Station,
    line: Line
): PrimaryKeyStaticEntity() {
    @Column(nullable = false)
    var distance: Double = distance
        protected set

    @Column(nullable = false)
    var duration: Int = duration
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var upStation: Station = upStation

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var downStation: Station = downStation


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var line: Line = line
}