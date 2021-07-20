package br.com.timeline.app.android.view.adapters

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.timeline.app.android.R
import br.com.timeline.app.android.entities.MenuType
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.TaskType
import br.com.timeline.app.android.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddTaskDialog( private val taskViewModel: TaskViewModel): DialogFragment() {

    companion object { const val TAG = "AddTaskDialog" }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.component_dialog, null))

            val list: ArrayList<MenuType> = ArrayList()

            list.add(MenuType(TaskType.MAIL, R.drawable.ic_mail))
            list.add(MenuType(TaskType.CALL, R.drawable.ic_phone))
            list.add(MenuType(TaskType.PROPOSAL, R.drawable.ic_file_text))
            list.add(MenuType(TaskType.REUNION, R.drawable.ic_icon_briefcase))
            list.add(MenuType(TaskType.VISIT, R.drawable.ic_map_pin))
            list.add(MenuType(TaskType.MORE, R.drawable.ic_more))

            val recyclerAdapter = MenuTypesRecyclerViewAdapter(this.requireContext(), list, taskViewModel)

            val recyclerLayoutManager: RecyclerView.LayoutManager
            recyclerLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val dialog: AlertDialog = alertDialog.create()

            dialog.show()

            val recyclerView: RecyclerView? = dialog.findViewById(R.id.rv_menu_types)

            val editTextDate: EditText? = dialog.findViewById(R.id.et_date)
            val editTextTime: EditText? = dialog.findViewById(R.id.et_time)
            val editTextClient: EditText? = dialog.findViewById(R.id.et_client)
            val editTextDescription: EditText? = dialog.findViewById(R.id.et_description)
            val buttonAddTask: Button? = dialog.findViewById(R.id.btn_add_task)

            val calendar = Calendar.getInstance()
            val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val formatDate = SimpleDateFormat("d MMM yyyy", Locale("pt", "BR"))

                editTextDate?.setText(formatDate.format(calendar.time))
            }

            val time = TimePickerDialog.OnTimeSetListener {_, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val formatDate = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

                editTextTime?.setText(formatDate.format(calendar.time))
            }

            editTextDate?.setOnClickListener {
                context?.let { cx -> DatePickerDialog(
                    cx,
                    date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()}
            }

            editTextTime?.setOnClickListener {
                context?.let { cx -> TimePickerDialog(
                    cx,
                    time,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    true
                ).show()}
            }

            recyclerView?.layoutManager = recyclerLayoutManager
            recyclerView?.adapter = recyclerAdapter

            var type: TaskType = TaskType.NONE
            taskViewModel.lastTaskTypeSelected.observe(this, { type = it })

            buttonAddTask?.setOnClickListener {
                val task = Task(0, editTextClient?.text.toString(), calendar.time.time, editTextDescription?.text.toString(), type)
                taskViewModel.insert(task)
                dialog.cancel()
            }

            return dialog
        }?:throw IllegalStateException("Activity is null.")
    }
}