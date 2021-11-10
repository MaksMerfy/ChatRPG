package ru.chatrpg.main.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "stats")
class Stat {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    var id: String = ""
    var name: String = ""
    var value: Int = 1

    constructor(name: String, value: Int) {
        this.name = name
        this.value = value
    }

}