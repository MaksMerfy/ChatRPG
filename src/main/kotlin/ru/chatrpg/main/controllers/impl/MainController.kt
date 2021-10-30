package ru.chatrpg.main.controllers.impl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.chatrpg.main.controllers.MainControllerApi

@RestController
class MainController: MainControllerApi {

    @GetMapping("/")
    override fun main(): String {
        return "Hi, my love! my wife!"
    }
}