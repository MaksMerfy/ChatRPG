package ru.chatrpg.main.services.impl

import com.sun.jdi.request.StepRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.dto.responses.impl.HeroResponseImpl
import ru.chatrpg.main.model.Hero
import ru.chatrpg.main.model.User
import ru.chatrpg.main.model.Stat
import ru.chatrpg.main.repositories.HeroRepository
import ru.chatrpg.main.services.AuthService
import ru.chatrpg.main.services.HeroService
import ru.chatrpg.main.services.StatService

@Service
class HeroServiceImpl : HeroService {
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
        for (stat in statService.findAllBaseStats()) {
            hero.stats.add(statService.save(Stat(stat.name, stat.value)))
        }
        return heroRepository.save(hero)
    }

    override fun findById(id: String): Hero? {
        return null
    }

    override fun findByUserNickname(): HeroResponse {
        val authResponse = authService.getNickNameFromAuthenticate()
        val heroResponse: HeroResponse = HeroResponseImpl()
        heroResponse.convertFromHero(heroRepository.findHeroByUserNickname(authResponse.nickname), mutableListOf<String>())
        return heroResponse
    }

    override fun updateHeroStats(stats: String?): HeroResponse {
        val errorMessages: MutableList<String> = mutableListOf<String>()
        val authResponse = authService.getNickNameFromAuthenticate()
        val heroResponse: HeroResponse = HeroResponseImpl()
        val hero = heroRepository.findHeroByUserNickname(authResponse.nickname)
        val stat = hero?.stats?.find { stat -> stat.name == stats }
        val expStat = hero?.stats?.find { item -> item.name == "experience" }
        if (stat != null && expStat != null) {
            val needExp = statService.needExpForUpdate(stat)
            if (needExp <= expStat.value) {
                expStat.value -= needExp
                statService.save(expStat)
                statService.update(stat)
                statService.save(stat)
            } else { errorMessages.add("Need experience $needExp") }
        }
        heroResponse.convertFromHero(hero, errorMessages)
        return heroResponse
    }
}