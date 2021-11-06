package ru.chatrpg.main.dto.responses.impl

import com.fasterxml.jackson.annotation.JsonGetter
import ru.chatrpg.main.dto.responses.HeroResponse

data class HeroResponseImpl(
    var nickname: String = "",
    var HP: Int = 0,
    var Mana: Int = 0,
    var Damage: Int = 0,
    var CriticalChance: Int = 0,
    var Armor: Int = 0,
    var Experience: Int = 0
) : HeroResponse {

    override fun getnickname(): String {
        return this.nickname
    }
    override fun gethp(): Int {
        return this.HP
    }
    override fun getmana(): Int {
        return this.Mana
    }
    override fun getdamage(): Int {
        return this.Damage
    }
    override fun getcriticalChance(): Int {
        return this.CriticalChance
    }
    override fun getarmor(): Int {
        return this.Armor
    }
    override fun getexperience(): Int {
        return this.Experience
    }
}