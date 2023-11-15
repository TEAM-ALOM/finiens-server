package com.example.alom1.finiensserver.domain.path

import com.example.alom1.finiensserver.domain.core.PrimaryKeyStaticEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name="subway_line")
class Line(
    lineName: String,
): PrimaryKeyStaticEntity() {
    @Column(name = "line_name", nullable = false, unique = true)
    var lineName: String = lineName
        protected set
}