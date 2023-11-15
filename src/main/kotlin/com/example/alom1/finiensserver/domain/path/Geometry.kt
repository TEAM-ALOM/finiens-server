package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.domain.core.PrimaryKeyStaticEntity
import com.example.alom1.finiensserver.domain.core.Coordinate
import jakarta.persistence.*

@Entity
@Table(name="subway_geometry")
class Geometry(
    coordinate: Coordinate,
    seq: Int,
    upStation: Station,
    downStation: Station,
    line: Line
): PrimaryKeyStaticEntity() {
    @Embedded
    var coordinate: Coordinate = coordinate

    @Column(nullable = false)
    var seq: Int = seq

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var upStation: Station = upStation

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var downStation: Station = downStation


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var line: Line = line
}