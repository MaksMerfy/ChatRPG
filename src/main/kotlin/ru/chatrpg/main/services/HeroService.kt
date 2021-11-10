package ru.chatrpg.main.services

import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.model.Hero
import ru.chatrpg.main.model.User

interface HeroService {
    fun saveNewHero(user: User): Hero?
    fun findById(id: String): Hero?
    fun findByUserNickname(): HeroResponse
    fun updateHeroStats(stats: String?) : HeroResponse
}