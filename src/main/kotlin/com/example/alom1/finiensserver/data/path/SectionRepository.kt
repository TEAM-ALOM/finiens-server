package com.example.alom1.finiensserver.data.path

import com.example.alom1.finiensserver.domain.path.Section
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SectionRepository: JpaRepository<Section, Long> {
}