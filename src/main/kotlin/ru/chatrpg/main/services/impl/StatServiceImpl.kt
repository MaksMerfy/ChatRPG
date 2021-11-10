package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.dto.responses.impl.StatQueryResponseImpl
import ru.chatrpg.main.model.Stat
import ru.chatrpg.main.repositories.StatRepository
import ru.chatrpg.main.services.StatService

@Service
class StatServiceImpl: StatService {
    @Autowired
    private lateinit var statRepository: StatRepository

    override fun save(stat: Stat): Stat {
        return statRepository.save(stat)
    }

    override fun findAllBaseStats(): MutableList<Stat> {
        return statRepository.findAllBaseStats()
    }

    override fun needExpForUpdate(stat: Stat): Int {
        val baseStat = statRepository.findBaseStatByName(stat.name)
        return 100 + (((stat.value - baseStat.getvalue())/baseStat.getstep()) * 10)
    }

    override fun update(stat: Stat) {
        val baseStat = statRepository.findBaseStatByName(stat.name)
        stat.value += baseStat.getstep()
    }
}