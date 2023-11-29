package com.example.alom1.finiensserver.data.path

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject

@JsonIgnoreProperties(ignoreUnknown = true)
data class BasicPathInfoDto(
    // meter 단위
    @get:JsonProperty("distance")
    val distance: Double,

    //초단위 double
    @get:JsonProperty("duration")
    val duration: Double,
) {

    companion object {
        private val mapper = jacksonObjectMapper()
        fun fromJson(jsonObject: JSONObject) : BasicPathInfoDto {
            val jsonArray: JSONArray = jsonObject.getJSONArray("routes")
            if (jsonArray.length() != 1) {
                throw Exception()
            }

            val json: JSONObject = jsonArray.getJSONObject(0)
            return mapper.readValue<BasicPathInfoDto>(json.toString(), BasicPathInfoDto::class.java)
        }
    }
}