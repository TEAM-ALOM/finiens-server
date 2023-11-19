package com.example.alom1.finiensserver.data.core

import aj.org.objectweb.asm.TypeReference
import com.example.alom1.finiensserver.domain.destination.Place
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionLikeType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.web.client.RestTemplate


abstract class RestTemplateClient(
    private val key: String? = null,
) {
    @Autowired
    lateinit var restTemplate: RestTemplate

    private fun getHeader(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        if (key != null) {
            headers.set("Authorization", "KakaoAK $key")
        }
        return headers
    }

    fun get(url: String): JSONObject {
        val httpEntity = HttpEntity<Any>(this.getHeader())
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            httpEntity,
            String::class.java
        )

        if (response.statusCode== HttpStatus.OK)
            return JSONObject(response.body)
        else
            throw Exception()
    }


//TODO:: get function inline으로 짜보기 list안에 generic넣으면 여기서 인식이 안되는데 그거 해결할 방법 찾아보기
//    inline fun <reified R, reified T> getList(url: String) : R {
////        val httpEntity = HttpEntity<Any>(this.getHeader())
////        val res = restTemplate.exchange(
////            url,
////            HttpMethod.GET,
////            httpEntity,
////            String::class.java
////        )
//        val body: String =
//            "{\"documents\":[{\"address_name\":\"서울 광진구 군자동 98\",\"category_group_code\":\"\",\"category_group_name\":\"\",\"category_name\":\"교육,학문 \u003e 학교부속시설\",\"distance\":\"6055\",\"id\":\"348460786\",\"phone\":\"\",\"place_name\":\"세종대학교 대양AI센터\",\"place_url\":\"http://place.map.kakao.com/348460786\",\"road_address_name\":\"서울 광진구 능동로 209\",\"x\":\"127.075528392327\",\"y\":\"37.5508561297125\"}] }"
//
//        val mapper = jacksonObjectMapper()
//        val collectionLikeType: CollectionLikeType = mapper.typeFactory.constructCollectionLikeType(R::class.java, T::class.java)
//        return mapper.readValue(JSONObject(body).getJSONArray("abc"), collectionLikeType)
//    }

}