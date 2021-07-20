package br.com.timeline.app.android.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.*
import br.com.timeline.app.android.R
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.TaskType
import java.text.SimpleDateFormat
import java.util.*

class TaskRecyclerViewAdapter : ListAdapter<Task, TaskRecyclerViewAdapter.TaskViewHolder>(TASKS_COMPARATOR) {
    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtWeekday: TextView = view.findViewById(R.id.txt_weekday)
        val txtDescription: TextView = view.findViewById(R.id.txt_description)
        val imgType: ImageView = view.findViewById(R.id.img_type)
        val txtHour: TextView = view.findViewById(R.id.txt_hour)
        val txtClient: TextView = view.findViewById(R.id.txt_client)
        val viewDividerBottom: View = view.findViewById(R.id.view_divider_bottom)

        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.component_task,
                    parent,
                    false
                )
                return TaskViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder = TaskViewHolder.create(parent)

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        val img = getImageTaskTypeByEnum(task.type)
        val weekday: String = SimpleDateFormat("EE", Locale("pt", "BR")).format(Date(task.date))
        val hoursAndMinutes = SimpleDateFormat("HH:mm").format(Date(task.date))

        when (position) {
            itemCount - 1 -> holder.viewDividerBottom.visibility = View.INVISIBLE
            else -> holder.viewDividerBottom.visibility = View.VISIBLE
        }

        holder.txtWeekday.text = weekday.replaceFirstChar { it.uppercase() }
        holder.txtHour.text = hoursAndMinutes
        holder.imgType.setImageResource(img)
        holder.txtDescription.text = task.description
        holder.txtClient.text = task.client
    }

    private fun getImageTaskTypeByEnum(type: TaskType) = when(type) {
        TaskType.CALL -> R.drawable.ic_phone
        TaskType.MAIL -> R.drawable.ic_mail
        TaskType.VISIT -> R.drawable.ic_map_pin
        TaskType.NONE -> R.drawable.ic_more
        TaskType.REUNION -> R.drawable.ic_icon_briefcase
        TaskType.PROPOSAL -> R.drawable.ic_file_text
        TaskType.MORE -> R.drawable.ic_more
    }

    companion object {
        private val TASKS_COMPARATOR = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.description == newItem.description
            }
        }
    }

    override fun getItemCount() = currentList.size
}