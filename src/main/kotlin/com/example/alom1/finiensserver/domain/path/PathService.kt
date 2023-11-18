package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.data.path.StationRepository
import com.example.alom1.finiensserver.domain.core.Coordinate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
    private val stationRepository: StationRepository
) {
    private fun getClosestStation(coordinate: Coordinate) : Station {
        return stationRepository.findClosestStation(
            inputLongitude = coordinate.longitude,
            inputLatitude = coordinate.latitude,
        )
    }

    private fun calculateSubwayWalkingRoute(coordinate: Coordinate, station: Station) {

    }

    fun findPath(departureCoordinate: Coordinate, destinationCoordinate: Coordinate) {
        val departureStation : Station = getClosestStation(coordinate = departureCoordinate)
        val destinationStation: Station = getClosestStation(coordinate = destinationCoordinate)

        departureStation.name
        destinationStation.name
    }
}