package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.Nullable
import ru.chatrpg.main.model.Hero

interface HeroRepository: JpaRepository<Hero, String> {
    @Nullable
    fun findHeroByUserNickname(nickname: String): Hero?
}