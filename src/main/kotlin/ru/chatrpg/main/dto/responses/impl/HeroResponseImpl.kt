package ru.chatrpg.main.dto.responses.impl

import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.model.Hero

data class HeroResponseImpl(
    var nickname: String = "",
    var listStats: MutableMap<String, Int> = mutableMapOf<String, Int>(),
    var errorMessages: MutableList<String> = mutableListOf<String>()
): HeroResponse {
    override fun convertFromHero(hero: Hero?, errorMessages: MutableList<String>) {
        if (hero != null) {
            this.nickname = hero.user.nickname
            for (stat in hero.stats) {
                this.listStats.put(stat.name, stat.value)
            }
            this.errorMessages = errorMessages
        }

    }
}