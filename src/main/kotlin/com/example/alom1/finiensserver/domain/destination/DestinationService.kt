package com.example.alom1.finiensserver.domain.destination

import com.example.alom1.finiensserver.domain.core.Coordinate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DestinationService @Autowired constructor(
    private val destinationRepository: DestinationRepository
) {
    fun findDestinationByKeyWord(keyWord: String, coordinate: Coordinate):List<Place> {
        return destinationRepository.findDestinationByKeyWord(keyWord = keyWord, coordinate = coordinate)
    }
}