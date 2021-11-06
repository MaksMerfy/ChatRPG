package ru.chatrpg.main.controllers

import org.springframework.web.bind.annotation.RequestParam
import ru.chatrpg.main.dto.responses.HeroResponse

interface HeroControllerApi {
    fun heroGet(): HeroResponse
    fun heroUpdatePost(@RequestParam(value = "Stats", required = false) stats: String) : HeroResponse
}