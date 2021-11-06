package ru.chatrpg.main.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.lang.Nullable
import ru.chatrpg.main.dto.responses.HeroResponse
import ru.chatrpg.main.model.Hero

interface HeroRepository: JpaRepository<Hero, String> {
    @Nullable
    @Query(
        value = "SELECT users.nickname as nickname, " +
                "heroes.hp as hp, " +
                "heroes.mana as mana, " +
                "heroes.damage as damage, " +
                "heroes.critical_chance as criticalchance, " +
                "heroes.armor as armor, " +
                "heroes.experience as experience " +
                "from users inner join heroes on users.id = heroes.user_id " +
                "where users.nickname = :nickname limit 1",
        nativeQuery = true
    )
    fun findByUserNickname(@Param("nickname") nickname: String): HeroResponse
    fun findHeroByUserNickname(nickname: String): Hero?
}