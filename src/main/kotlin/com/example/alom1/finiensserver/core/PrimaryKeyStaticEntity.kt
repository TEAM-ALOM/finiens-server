package com.example.alom1.finiensserver.core

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class PrimaryKeyStaticEntity: PrimaryKeyEntity<Long>() {
    @Id
    @Column(name = "id")
    override var id: Long = 1
}