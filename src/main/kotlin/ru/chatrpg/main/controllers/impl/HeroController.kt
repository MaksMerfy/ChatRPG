package ru.chatrpg.main.controllers.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.chatrpg.main.controllers.HeroControllerApi
import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.services.HeroService

@RestController
class HeroController: HeroControllerApi {
    @Autowired
    private lateinit var heroService: HeroService

    @GetMapping("/hero")
    override fun heroGet(): HeroResponse {
        return heroService.findByUserNickname()
    }

    @PostMapping("/hero/update")
    override fun heroUpdatePost(@RequestParam(value = "Stats", required = false) stats: String): HeroResponse {
        return heroService.updateHeroStats(stats)
    }
}