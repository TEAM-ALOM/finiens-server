package com.example.alom1.finiensserver.presentation.path

import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.path.PathService
import com.example.alom1.finiensserver.presentation.path.dto.PathDto
import com.example.alom1.finiensserver.presentation.path.dto.TravelPathResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PathPresentation @Autowired constructor(
    private val pathService: PathService
) {

    @GetMapping("places/directions")
    fun findPaths(
        @RequestParam("departure-latitude") departureLatitude : Double,
        @RequestParam("departure-longitude") departureLongitude : Double,
        @RequestParam("destination-latitude") destinationLatitude: Double,
        @RequestParam("destination-longitude") destinationLongitude: Double
    ): ResponseEntity<List<TravelPathResponseDto>> {
            val departureCoordinate: Coordinate = Coordinate(latitude = departureLatitude, longitude = departureLongitude)
            val destinationCoordinate: Coordinate = Coordinate(latitude = destinationLatitude, longitude = destinationLongitude)

            val data = pathService.findPaths(departureCoordinate=departureCoordinate, destinationCoordinate=destinationCoordinate)
            return ResponseEntity.ok().body(
                data
            )
    }

//    @PostMapping("places/directions")
//    fun findPathGeometry(
//        @RequestBody
//    ) {
//    }
}