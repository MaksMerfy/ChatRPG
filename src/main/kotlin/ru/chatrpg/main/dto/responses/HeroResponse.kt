package ru.chatrpg.main.dto.responses

import ru.chatrpg.main.model.Hero

interface HeroResponse {
   fun convertFromHero(hero: Hero?, errorMessages: MutableList<String>)
}