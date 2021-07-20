package br.com.timeline.app.android.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.timeline.app.android.utils.TaskType
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val client: String,
    val date: Long = Date().time,
    val description: String,
    val type: TaskType = TaskType.NONE
    ) {

    override fun toString(): String {
        return "Id: $id, Cliente: $client, Data: $date, Descrição: $description, Type: $type"
    }
}