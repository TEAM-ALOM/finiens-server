package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.domain.station.Station
import org.jgrapht.GraphPath
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.springframework.stereotype.Service

@Service
class PathFinder {
    companion object {
        var subwayGraph: SubwayGraph? = null
        fun initSubwayGraph(stations: List<Station>, sections: List<Section> ) {
            subwayGraph = SubwayGraph()
            subwayGraph!!.addVertixByStation(stations=stations)
            subwayGraph!!.addEdgeBySection(sections=sections)
        }
    }

    fun findPath(source: Station, target: Station): List<Section> {
        // 제일 빠른 경로 찾기
        val shortestPath: GraphPath<Station, Section> =  DijkstraShortestPath.findPathBetween(subwayGraph, source, target)

        return shortestPath.edgeList
    }

//    fun find() {
//
//    }
}