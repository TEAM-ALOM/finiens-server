package com.example.alom1.finiensserver.data.path

import com.example.alom1.finiensserver.domain.core.Coordinate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject

@JsonIgnoreProperties(ignoreUnknown = true)
data class DetailedPathInfoDto private constructor(
    @get:JsonProperty("distance")
    val distance: Double,
    @get:JsonProperty("duration")
    val duration: Double,
    //TODO::이게 효과적인지 모르겠음 coordinate라는 dto를 따로 만들어야 할지
    var coordinates: List<Coordinate> = listOf()
) {
    @JsonProperty("geometry")
    fun unpackNameFromNestedObject(json: LinkedHashMap<String, Any>) {
        val jsonCoordinates: List<List<Double>> = json["coordinates"] as List<List<Double>>

        coordinates = jsonCoordinates.map { it ->
            Coordinate(latitude = it[1], longitude = it[0])
        }
    }



    companion object {
        private val mapper = jacksonObjectMapper()
        fun fromJson(jsonObject: JSONObject) : DetailedPathInfoDto {
            val jsonArray: JSONArray = jsonObject.getJSONArray("routes")
            if (jsonArray.length() != 1) {
                throw Exception()
            }

            val json: JSONObject = jsonArray.getJSONObject(0)
            return mapper.readValue<DetailedPathInfoDto>(json.toString(), DetailedPathInfoDto::class.java)
        }
    }
}