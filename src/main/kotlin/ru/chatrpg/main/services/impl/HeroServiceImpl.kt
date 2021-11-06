package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.dto.responses.impl.HeroResponseImpl
import ru.chatrpg.main.model.Hero
import ru.chatrpg.main.model.User
import ru.chatrpg.main.repositories.HeroRepository
import ru.chatrpg.main.services.AuthService
import ru.chatrpg.main.services.HeroService
import kotlin.reflect.KProperty1

@Service
class HeroServiceImpl: HeroService {
    @Autowired
    private lateinit var heroRepository: HeroRepository

    @Autowired
    private lateinit var authService: AuthService

    override fun saveNewHero(user: User): Hero? {
        val hero = Hero()
        hero.user = user
        return heroRepository.save(hero)
    }

    override fun findById(id: String): Hero? {
        return null
    }

    override fun findByUserNickname(): HeroResponse {
        val authResponse = authService.getNickNameFromAuthenticate()
        return heroRepository.findByUserNickname(authResponse.nickname) ?: HeroResponseImpl()
    }

    override fun updateHeroStats(stats: String): HeroResponse {
        val authResponse = authService.getNickNameFromAuthenticate()
        val heroResponse: HeroResponse = HeroResponseImpl()
        val hero = heroRepository.findHeroByUserNickname(authResponse.nickname)
        if (hero != null){
        }
        return heroResponse
    }
}