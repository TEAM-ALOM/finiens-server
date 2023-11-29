package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.data.path.BasicPathInfoDto
import com.example.alom1.finiensserver.data.path.OSRMRepository
import com.example.alom1.finiensserver.data.path.SectionRepository
import com.example.alom1.finiensserver.data.path.StationRepository
import com.example.alom1.finiensserver.domain.core.Coordinate
import com.example.alom1.finiensserver.domain.station.Station
import com.example.alom1.finiensserver.presentation.path.dto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
    private val stationRepository: StationRepository,
    private val sectionRepository: SectionRepository,
    private val osrmRepository: OSRMRepository,
    private val pathFinder: PathFinder
) {
    private fun getClosestStations(departureCoordinate: Coordinate, destinationCoordinate: Coordinate) : List<Station> {
        return stationRepository.findClosestStations(
            departureLatitude = departureCoordinate.latitude,
            departureLongitude = departureCoordinate.longitude,
            destinationLatitude = destinationCoordinate.latitude,
            destinationLongitude = destinationCoordinate.longitude
        )
    }

    private fun calculateBasicPath(coordinate: Coordinate, station: Station) : BasicPathInfoDto {
        return osrmRepository.getBasicPathInfo(departureCoordinate = coordinate, destinationCoordinate = station.coordinate)
    }

    fun findPaths(departureCoordinate: Coordinate, destinationCoordinate: Coordinate): List<PathDto> {
        //출발 정류장 검색
        //현재 위치로 부터 정류장 까지 가는 시간 검색
        val closestStation : List<Station> = getClosestStations(
            departureCoordinate = departureCoordinate,
            destinationCoordinate = destinationCoordinate
        ) // 이건 db

        if (closestStation.size == 1) {
            calculatePathByFoot()
        }

        val departureStation: Station = closestStation[0]
        val departureBasicPathInfo: BasicPathInfoDto =
            calculateBasicPath(coordinate = departureCoordinate, station = departureStation) // 이건  외부 api




        val destinationStation: Station = closestStation[1]
        val destinationBasicPathInfo: BasicPathInfoDto =
            calculateBasicPath(coordinate = destinationCoordinate, station = destinationStation)

        // TODO:: 이걸 어떻게 처리할지 subwaygraph 자체를 db에 집어 넣으면 좋을거 같음
        if (PathFinder.subwayGraph == null) {
            val allStations: List<Station> = stationRepository.findAll()
            val allSections: List<Section> = sectionRepository.findAll()
            PathFinder.initSubwayGraph(allStations, allSections)
        }

        val sectionPaths: List<Section> = pathFinder.findPath(departureStation, destinationStation)


        val result: MutableList<PathDto> = mutableListOf()

        var routeCount = 0
        val departureFootPathDto: FootPathDto = FootPathDto(
            routeCount = routeCount,
            footRouteDto = FootRouteDto(
                distance = departureBasicPathInfo.distance,
                duration = departureBasicPathInfo.duration.toInt()
            )
        )
        routeCount++
        result.add(departureFootPathDto)


        val transferStations: List<Station> = getTransferStations(sections = sectionPaths)

        splitSectionByLine(
            transferStations=transferStations,
            sectionPaths=sectionPaths,
            destinationStation = destinationStation,
            startCount = routeCount
        ).forEach {
            result.add(it)
            routeCount++
        }


        val destinationFootPathDto: FootPathDto = FootPathDto(
            routeCount = routeCount,
            footRouteDto = FootRouteDto(
                distance = destinationBasicPathInfo.distance,
                duration = destinationBasicPathInfo.duration.toInt()
            )
        )

        result.add(destinationFootPathDto)

        return result
    }

    private fun getTransferStations(sections: List<Section>): List<Station> {
        val transferStation: MutableList<Station> = mutableListOf<Station>()
        for (i in 0 until sections.size - 1) {
            if (sections[i].line.id != sections[i + 1].line.id) {
                if (sections[i].upStation == sections[i + 1].upStation || sections[i].upStation == sections[i + 1].downStation)
                    transferStation.add(sections[i].upStation)
                else
                    transferStation.add(sections[i].downStation)
            }
        }

        return transferStation
    }


    // TODO:: 아래 함수 리팩토링
    private fun splitSectionByLine(
        transferStations: List<Station>,
        sectionPaths: List<Section>,
        destinationStation: Station,
        startCount: Int
    ): List<SubwayPathDto> {
        val result: MutableList<SubwayPathDto> = mutableListOf()
        var routeCount: Int = startCount
        var j = 0
        var duration: Int
        var distance: Double
        var stationResponseDtos: MutableList<StationResponseDto> = mutableListOf()
        for (i in transferStations.indices) {
            duration = 0
            distance = 0.0
            stationResponseDtos = mutableListOf()
            while (true) {
                var startStation: Station
                var endStation: Station
                if (sectionPaths[j].upStation == sectionPaths[j + 1].upStation ||
                    sectionPaths[j].upStation == sectionPaths[j + 1].downStation) {
                    startStation = sectionPaths[j].downStation
                    endStation = sectionPaths[j].upStation
                } else {
                    startStation = sectionPaths[j].upStation
                    endStation = sectionPaths[j].downStation
                }
                val stationResponseDto: StationResponseDto = StationResponseDto(
                    id = startStation.id,
                    stationName = startStation.name
                )
                duration += sectionPaths[j].duration
                distance += sectionPaths[j].distance
                j++
                stationResponseDtos.add(stationResponseDto)
                if (transferStations[i] == endStation) {
                    val stationResponseDto1: StationResponseDto = StationResponseDto(
                        id = endStation.id,
                        stationName = endStation.name
                    )
                    stationResponseDtos.add(stationResponseDto1)
                    break
                }
            }
            val subwayPathDto: SubwayPathDto = SubwayPathDto(
                routeCount = routeCount,
                subwayRouteDto = SubwayRouteDto(
                    duration = duration,
                    distance = distance,
                    stations = stationResponseDtos,
                ),
                lineName = sectionPaths[j - 1].line.lineName
            )
            result.add(subwayPathDto)
            routeCount++
        }
        duration = 0
        distance = 0.0
        stationResponseDtos = mutableListOf()
        while (j < sectionPaths.size) {
            if (j == sectionPaths.size - 1) {
                var startStation: Station
                var endStation: Station
                if (destinationStation == sectionPaths[j].upStation) {
                    startStation = sectionPaths[j].downStation
                    endStation = sectionPaths[j].upStation
                } else {
                    startStation = sectionPaths[j].upStation
                    endStation = sectionPaths[j].downStation
                }

                var stationResponseDto: StationResponseDto

                stationResponseDto = StationResponseDto(
                    id = startStation.id,
                    stationName = startStation.name
                )
                stationResponseDtos.add(stationResponseDto)
                stationResponseDto = StationResponseDto(
                    id = endStation.id,
                    stationName = endStation.name
                )
                stationResponseDtos.add(stationResponseDto)
                break
            }
            var startStation: Station
            if (sectionPaths[j].upStation == sectionPaths[j + 1].upStation ||
                sectionPaths[j].upStation == sectionPaths[j + 1].downStation) {
                startStation = sectionPaths[j].downStation
            } else {
                startStation = sectionPaths[j].upStation
            }
            val stationResponseDto: StationResponseDto = StationResponseDto(
                id = startStation.id,
                stationName = startStation.name
            )
            duration += sectionPaths[j].duration
            distance += sectionPaths[j].distance
            j++
            stationResponseDtos.add(stationResponseDto)
        }
        val subwayPathDto: SubwayPathDto = SubwayPathDto(
            routeCount = routeCount,
            subwayRouteDto = SubwayRouteDto(
                duration = duration,
                distance = distance,
                stations = stationResponseDtos,
            ),
            lineName = sectionPaths[j].line.lineName
        )
        result.add(subwayPathDto)
        routeCount++

        return result
    }

    private fun calculatePathByFoot() {}
}