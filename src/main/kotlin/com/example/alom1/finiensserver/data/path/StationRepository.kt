package com.example.alom1.finiensserver.data.path

import com.example.alom1.finiensserver.domain.station.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StationRepository: JpaRepository<Station, Long> {
    @Query(
        value = "SELECT s.* from subway_station as s " +
                "where s.id = " +
                "(SELECT s1.id FROM subway_station as s1 ORDER BY " +
                "(POW((69.1 * (s1.latitude - :departureLatitude)), 2) + POW((69.1 * (s1.longitude - :departureLongitude) * COS(s1.latitude / 57.3)), 2)) " +
                "LIMIT :number) " +
                "UNION ALL " +
                "(SELECT s.* from subway_station as s " +
                "where s.id = " +
                "(SELECT s2.id FROM subway_station as s2 ORDER BY " +
                "(POW((69.1 * (s2.latitude - :destinationLatitude)), 2) + POW((69.1 * (s2.longitude - :destinationLongitude) * COS(s2.latitude / 57.3)), 2)) " +
                "LIMIT :number));",
        nativeQuery = true
    )
    fun findClosestStations(
        departureLatitude:Double,
        departureLongitude: Double,
        destinationLatitude:Double,
        destinationLongitude: Double,
        number : Int = 1
    ) : List<Station>
}