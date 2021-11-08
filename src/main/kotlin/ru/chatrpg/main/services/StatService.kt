package ru.chatrpg.main.services

import ru.chatrpg.main.model.stats.Stat

interface StatService {
    fun save(stat: Stat): Stat
}