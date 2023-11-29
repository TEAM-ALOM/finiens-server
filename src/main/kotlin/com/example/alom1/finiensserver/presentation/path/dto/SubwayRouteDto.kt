package com.example.alom1.finiensserver.presentation.path.dto

import com.example.alom1.finiensserver.domain.path.Section

class SubwayRouteDto(
    duration: Int,
    distance: Double,
    val stations: List<StationResponseDto>
): RouteDto(duration = duration, distance = distance) {

//    fun sectionsToDto(sections: List<Section>) : SubwayRouteDto {
//        var duration = 0
//        var distance = 0.0
//        sections.forEach {
//            duration += it.duration
//            distance += it.distance
//        }
//
//        return SubwayRouteDto(
//            duration = duration,
//            distance = distance,
//            stations =
//        )
//    }
}