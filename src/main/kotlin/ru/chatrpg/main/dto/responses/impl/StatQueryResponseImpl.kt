package ru.chatrpg.main.dto.responses.impl

import ru.chatrpg.main.dto.responses.StatQueryResponse

data class StatQueryResponseImpl(
    var id: String = "",
    var name: String = "",
    var value: Int = 0,
    var step: Int = 0
) : StatQueryResponse {

    override fun getid(): String {
        return this.id
    }

    override fun getname(): String {
        return this.name
    }

    override fun getvalue(): Int {
        return this.value
    }

    override fun getstep(): Int {
        return this.step
    }
}