package com.example.alom1.finiensserver.data.destination

import com.example.alom1.finiensserver.data.core.RestTemplateClient
import com.example.alom1.finiensserver.domain.destination.Place
import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.destination.DestinationRepository
import com.fasterxml.jackson.databind.type.CollectionLikeType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Repository

@Repository
class DestinationRepositoryImpl @Autowired constructor(
    @Value("\${spring.k_search.access_key}")
    private val key: String,
    @Value("\${spring.k_search.url}")
    private val url: String,
): DestinationRepository, RestTemplateClient(key=key) {
    private val mapper = jacksonObjectMapper()
    private val listPlaceType: CollectionLikeType = mapper.typeFactory.constructCollectionLikeType(List::class.java, DestinationResponseDto::class.java)

    override fun findDestinationByKeyWord(keyWord: String, coordinate: Coordinate):List<Place> {
        val urlWithParam: String = "$url?query=$keyWord&x=${coordinate.longitude}&y=${coordinate.latitude}"

        val jsonResult : JSONObject = get(urlWithParam)
        return jsonObjectToEntity(jsonObject = jsonResult)
    }

    private fun jsonObjectToEntity(jsonObject: JSONObject): List<Place> {
        val jsonArray: JSONArray = jsonObject.getJSONArray("documents")

        val dto = mapper.readValue<List<DestinationResponseDto>>(jsonArray.toString(), listPlaceType)

        return dto.map { d -> d.toEntity()}
    }
}
