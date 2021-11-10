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
    @OneToMany
    lateinit var stats: MutableList<Stat>
}