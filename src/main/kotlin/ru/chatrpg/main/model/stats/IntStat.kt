package ru.chatrpg.main.model.stats

interface IntStat {
    fun needExpForUpdate() : Int
    fun update()
}