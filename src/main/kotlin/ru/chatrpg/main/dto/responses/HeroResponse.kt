package ru.chatrpg.main.dto.responses

import com.fasterxml.jackson.annotation.JsonGetter

interface HeroResponse {
    @JsonGetter
    fun getnickname(): String
    @JsonGetter
    fun gethp(): Int
    @JsonGetter
    fun getmana(): Int
    @JsonGetter
    fun getdamage(): Int
    @JsonGetter
    fun getcriticalChance(): Int
    @JsonGetter
    fun getarmor(): Int
    @JsonGetter
    fun getexperience(): Int
}