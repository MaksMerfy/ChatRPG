package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.dto.responses.impl.HeroResponseImpl
import ru.chatrpg.main.model.Hero
import ru.chatrpg.main.model.User
import ru.chatrpg.main.model.stats.ListStats
import ru.chatrpg.main.model.stats.Stat
import ru.chatrpg.main.repositories.HeroRepository
import ru.chatrpg.main.services.AuthService
import ru.chatrpg.main.services.HeroService
import ru.chatrpg.main.services.StatService

@Service
class HeroServiceImpl: HeroService {
    @Autowired
    private lateinit var heroRepository: HeroRepository

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var statService: StatService

    override fun saveNewHero(user: User): Hero? {
        val hero = Hero()
        hero.user = user
        hero.stats = mutableListOf<Stat>()
        for (curStat in ListStats.values()){
            var stat = Stat()
            stat.name = curStat.name
            stat.value = 1
            stat = statService.save(stat)
            hero.stats.add(stat)
        }
        return heroRepository.save(hero)
    }

    override fun findById(id: String): Hero? {
        return null
    }

    override fun findByUserNickname(): HeroResponse {
        val authResponse = authService.getNickNameFromAuthenticate()
        val heroResponse: HeroResponse = HeroResponseImpl()
        heroResponse.convertFromHero(heroRepository.findHeroByUserNickname(authResponse.nickname))
        return heroResponse
    }

    override fun updateHeroStats(stats: String): HeroResponse {
        val authResponse = authService.getNickNameFromAuthenticate()
        val heroResponse: HeroResponse = HeroResponseImpl()
        val hero = heroRepository.findHeroByUserNickname(authResponse.nickname)
        val stat = hero?.stats?.find { stat -> stat.name == stats }
        if (stat != null){
            stat.update()
            statService.save(stat)
        }
        heroResponse.convertFromHero(hero)
        return heroResponse
    }
}