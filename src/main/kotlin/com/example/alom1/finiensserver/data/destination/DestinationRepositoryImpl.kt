package com.example.alom1.finiensserver.data.destination

import com.example.alom1.finiensserver.data.core.RestTemplateClient
import com.example.alom1.finiensserver.domain.destination.Place
import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.destination.DestinationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Repository

@Repository
class DestinationRepositoryImpl @Autowired constructor(
    @Value("\${spring.search.access_key}")
    private val key: String,
    @Value("\${spring.search.url}")
    private val url: String,
): DestinationRepository, RestTemplateClient(key=key) {
    private val baseUrl = "https://dapi.kakao.com/v2/local/"


    override fun findDestinationByKeyWord(keyWord: String, coordinate: Coordinate):List<Place> {
        val urlWithParam: String = "$url?$keyWord&x=${coordinate.longitude}&y=${coordinate.longitude}"

        val result: List<Place> = get<List<Place>>(urlWithParam)

        return result
    }
}
