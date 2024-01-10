package com.example.alom1.finiensserver.data.path

import com.example.alom1.finiensserver.data.core.RestTemplateClient
import com.example.alom1.finiensserver.domain.core.Coordinate
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class OSRMRepository @Autowired constructor(

) : RestTemplateClient() {
    val baseUrl: String = "http://router.project-osrm.org/route/v1/"
//    val basicPathUrl: String = baseUrl + "foot/"


    fun getDetailedPathInfo(departureCoordinate: Coordinate, destinationCoordinate: Coordinate): DetailedPathInfoDto {
        val url : String = baseUrl + "foot/" +
                "${departureCoordinate.longitude}," +
                "${departureCoordinate.latitude};" +
                "${destinationCoordinate.longitude}," +
                "${destinationCoordinate.latitude}" +
                "?overview=full&geometries=geojson"

        val dto: DetailedPathInfoDto = DetailedPathInfoDto.fromJson(get(url))

        return dto
    }

    fun getBasicPathInfo(departureCoordinate: Coordinate, destinationCoordinate: Coordinate) : BasicPathInfoDto {
        val url : String = baseUrl + "foot/" +
                "${departureCoordinate.longitude}," +
                "${departureCoordinate.latitude};" +
                "${destinationCoordinate.longitude}," +
                "${destinationCoordinate.latitude}" +
                "?overview=false&geometries=geojson"

        val dto : BasicPathInfoDto = BasicPathInfoDto.fromJson(get(url))
        return dto
    }
}