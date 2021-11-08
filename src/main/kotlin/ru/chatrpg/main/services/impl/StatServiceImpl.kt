package ru.chatrpg.main.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.chatrpg.main.model.stats.Stat
import ru.chatrpg.main.repositories.StatRepository
import ru.chatrpg.main.services.StatService

@Service
class StatServiceImpl: StatService {
    @Autowired
    private lateinit var statRepository: StatRepository

    override fun save(stat: Stat): Stat {
        return statRepository.save(stat)
    }
}