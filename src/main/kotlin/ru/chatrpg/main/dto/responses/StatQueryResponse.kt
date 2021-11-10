package ru.chatrpg.main.dto.responses

import com.fasterxml.jackson.annotation.JsonGetter

interface StatQueryResponse {
    @JsonGetter
    fun getid():String
    @JsonGetter
    fun getname(): String
    @JsonGetter
    fun getvalue(): Int
    @JsonGetter
    fun getstep(): Int
}