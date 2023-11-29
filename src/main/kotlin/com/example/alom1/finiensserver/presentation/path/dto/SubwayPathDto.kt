package com.example.alom1.finiensserver.presentation.path.dto

import com.example.alom1.finiensserver.domain.path.TransportationType

class SubwayPathDto(
    routeCount: Int,
    subwayRouteDto: SubwayRouteDto,
    val lineName: String
): PathDto(
    routeCount=routeCount,
    transportationType = TransportationType.SUBWAY.number,
    routeDto = subwayRouteDto
) {
}