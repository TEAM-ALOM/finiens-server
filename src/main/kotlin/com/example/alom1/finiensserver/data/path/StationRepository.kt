package com.example.alom1.finiensserver.data.path

import com.example.alom1.finiensserver.domain.path.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StationRepository: JpaRepository<Station, Long> {
    @Query(
        value = "SELECT s.*, " +
                "(6371 * acos( cos( radians(:inputLatitude) ) " +
                "* cos( radians( s.latitude ) ) " +
                "* cos( radians( s.longitude ) - radians(:inputLongitude) ) " +
                "+ sin( radians(:inputLatitude) ) " +
                "* sin( radians( s.latitude ) ) ) ) " +
                "AS distance " +
                "FROM subway_station AS s " +
                "ORDER BY distance " +
                "LIMIT :number",
        nativeQuery = true
    )
    fun findClosestStation(inputLatitude:Double, inputLongitude: Double, number : Int = 1) : Station
}