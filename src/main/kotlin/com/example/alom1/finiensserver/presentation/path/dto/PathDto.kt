package com.example.alom1.finiensserver.presentation.path.dto

import com.example.alom1.finiensserver.domain.path.TransportationType

sealed class PathDto(
    val routeCount: Int,
    val transportationType: Int,
    val routeDto: RouteDto
)