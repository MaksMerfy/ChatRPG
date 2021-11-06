package ru.chatrpg.main.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "heroes")
class Hero {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    var id: String = ""

    @OneToOne
    lateinit var user: User
    var hp: Int = 100
    var mana: Int = 10
    var damage: Int = 1
    var armor: Int = 1
    var criticalChance: Int = 0
    var experience: Int = 0
}