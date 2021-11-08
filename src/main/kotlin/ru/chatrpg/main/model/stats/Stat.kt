package ru.chatrpg.main.model.stats

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "stats")
class Stat: IntStat {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    var id: String = ""
    var name: String = ""
    var value: Int = 1

    override fun needExpForUpdate(): Int {
        return 100 + ((this.value - 1) * 10)
    }

    override fun update() {
        this.value += 1
    }
}