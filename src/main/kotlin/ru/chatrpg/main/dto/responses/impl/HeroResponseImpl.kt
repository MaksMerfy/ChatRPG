package ru.chatrpg.main.dto.responses.impl

import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.model.Hero

data class HeroResponseImpl(
    var nickname: String = "",
    var strength: Int = 0,
    var dexterity: Int = 0,
    var intellegy: Int = 0
): HeroResponse {
    override fun convertFromHero(hero: Hero?) {
        if (hero != null) {
            this.nickname = hero.user.nickname
            for (stat in hero.stats) {
                when (stat.name) {
                    "strength" -> this.strength = stat.value
                    "dexterity" -> this.dexterity = stat.value
                    "intellegy" -> this.intellegy = stat.value
                }
            }
        }

    }
}