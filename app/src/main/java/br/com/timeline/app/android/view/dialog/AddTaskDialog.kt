package br.com.timeline.app.android.view.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.timeline.app.android.R
import br.com.timeline.app.android.entities.MenuType
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.format.FormatDate
import br.com.timeline.app.android.utils.enums.TaskType
import br.com.timeline.app.android.utils.mock.ListMock
import br.com.timeline.app.android.view.adapters.MenuTypesRecyclerViewAdapter
import br.com.timeline.app.android.viewmodel.TaskViewModel
import java.util.*
import kotlin.collections.ArrayList


class AddTaskDialog( private val taskViewModel: TaskViewModel): DialogFragment() {

    companion object { const val TAG = "AddTaskDialog" }

    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var editTextClient: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var taskType: TaskType

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(requireActivity().layoutInflater.inflate(R.layout.component_dialog, null))

            val list: ArrayList<MenuType> = ListMock.getMenuTypes()

            val recyclerAdapter = MenuTypesRecyclerViewAdapter(this.requireContext(), list, taskViewModel)
            val recyclerLayoutManager: RecyclerView.LayoutManager
            recyclerLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            val dialog: AlertDialog = alertDialog.create()
            dialog.show()

            initViews(dialog)

            val calendar = Calendar.getInstance()

            setDateListener(calendar)

            setTimeListener(calendar)

            recyclerView.layoutManager = recyclerLayoutManager
            recyclerView.adapter = recyclerAdapter

            taskType = TaskType.NONE
            taskViewModel.lastTaskTypeSelected.observe(this, { taskType = it })

            buttonAddTask.setOnClickListener {
                if (formIsValid()) {
                    val task = Task(
                        0,
                        editTextClient.text.toString(),
                        calendar.time.time,
                        editTextDescription.text.toString(),
                    taskType
                    )
                    taskViewModel.insert(task)
                    dialog.cancel()
                } else {
                    Toast.makeText(
                        context,
                        "Por favor, verifique se selecionou e preencheu todos os campos.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            return dialog
        }?:throw IllegalStateException("Activity is null.")
    }


    private fun initViews(dialog: AlertDialog) {
        recyclerView = dialog.findViewById(R.id.rv_menu_types)
        editTextDate = dialog.findViewById(R.id.et_date)
        editTextTime = dialog.findViewById(R.id.et_time)
        editTextClient = dialog.findViewById(R.id.et_client)
        editTextDescription = dialog.findViewById(R.id.et_description)
        buttonAddTask = dialog.findViewById(R.id.btn_add_task)
    }

    private fun setDateListener(calendar: Calendar) {
        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormatted = FormatDate.get(calendar.time)

            editTextDate.setText(dateFormatted)
        }

        editTextDate.setOnClickListener {
            context?.let { cx ->
                val picker = DatePickerDialog(
                    cx,
                    date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                picker.datePicker.minDate = System.currentTimeMillis() - 1000;
                picker.show()
            }
        }
    }

    private fun setTimeListener(calendar: Calendar) {
        val time = TimePickerDialog.OnTimeSetListener {_, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            val timeFormatted = FormatDate.getTime(calendar.time)

            editTextTime.setText(timeFormatted)
        }

        editTextTime.setOnClickListener {
            context?.let { cx -> TimePickerDialog(
                cx,
                time,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                true
            ).show()}
        }
    }

    fun formIsValid(): Boolean =
        (!editTextClient.text.isNullOrBlank() || !editTextClient.text.isNullOrEmpty()) &&
        (!editTextDescription.text.isNullOrBlank() || !editTextDescription.text.isNullOrEmpty()) &&
        (!editTextDate.text.isNullOrBlank() || !editTextDate.text.isNullOrEmpty()) &&
        (!editTextTime.text.isNullOrBlank() || !editTextTime.text.isNullOrEmpty()) && taskType != TaskType.NONE


}