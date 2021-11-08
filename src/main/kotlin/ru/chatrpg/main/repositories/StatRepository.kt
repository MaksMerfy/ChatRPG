package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.chatrpg.main.model.stats.Stat

interface StatRepository: JpaRepository<Stat, String> {
}