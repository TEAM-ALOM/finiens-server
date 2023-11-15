package com.example.alom1.finiensserver.domain.destination

import com.example.alom1.finiensserver.domain.core.Coordinate

interface DestinationRepository {
    fun findDestinationByKeyWord(keyWord: String, coordinate: Coordinate): List<Place>
}