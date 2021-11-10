package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.lang.Nullable
import ru.chatrpg.main.dto.responses.StatQueryResponse
import ru.chatrpg.main.model.Stat

interface StatRepository: JpaRepository<Stat, String> {
    @Query(value = "SELECT * FROM basestats", nativeQuery = true)
    fun findAllBaseStats(): MutableList<Stat>

    @Nullable
    @Query(value = "SELECT * FROM basestats where name  = :name", nativeQuery = true)
    fun findBaseStatByName(@Param("name") name: String): StatQueryResponse
}