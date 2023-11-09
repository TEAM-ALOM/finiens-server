package com.example.alom1.finiensserver.core

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID

@MappedSuperclass
abstract class PrimaryKeyDynamicEntity: PrimaryKeyEntity<UUID>() {
    @Id
    @Column(columnDefinition = "BINARY(16)", name = "id")
    override var id: UUID =  UlidCreator.getMonotonicUlid().toUuid()

}