package ru.chatrpg.main.services

import ru.chatrpg.main.model.Stat

interface StatService {
    fun save(stat: Stat): Stat
    fun findAllBaseStats(): MutableList<Stat>
    fun needExpForUpdate(stat: Stat): Int
    fun update(stat: Stat)
}