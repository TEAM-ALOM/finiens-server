package com.example.alom1.finiensserver.presentation

import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.destination.DestinationService
import com.example.alom1.finiensserver.domain.destination.Place
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DestinationPresentation @Autowired constructor(
    private val destinationService: DestinationService
) {

    @GetMapping("/places/destination")
    fun findDestinations(
        @RequestParam("place-name") placeName: String,
        @RequestParam latitude: Double,
        @RequestParam longitude: Double
    ): List<Place> {
        println("start")
        return destinationService.findDestinationByKeyWord(
            keyWord = placeName,
            coordinate = Coordinate(latitude=latitude, longitude=longitude)
        )
    }
}