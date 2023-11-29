package com.example.alom1.finiensserver.presentation.path.dto

import com.example.alom1.finiensserver.domain.path.TransportationType

class FootPathDto(
    routeCount: Int,
    footRouteDto: FootRouteDto
) : PathDto(
    routeCount = routeCount,
    transportationType= TransportationType.FOOT.number,
    routeDto = footRouteDto
) {

}