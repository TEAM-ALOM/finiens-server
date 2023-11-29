package com.example.alom1.finiensserver.presentation.path.dto


class FootRouteDto(
    distance: Double,
    duration: Int
): RouteDto(
    duration = duration,
    distance = distance
) {
}