package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.domain.station.Station
import org.jgrapht.graph.WeightedMultigraph

class SubwayGraph(): WeightedMultigraph<Station, Section>(Section::class.java) {
    fun addVertixByStation(stations : List<Station>) {
        stations.forEach {
            addVertex(it)
        }
    }

    fun addEdgeBySection(sections: List<Section>) {
        sections.forEach {
            addEdge(it.upStation, it.downStation, it)
            setEdgeWeight(it, it.duration.toDouble())
        }
    }
}