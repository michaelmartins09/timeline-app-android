package br.com.timeline.app.android.view.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.*
import br.com.timeline.app.android.R
import br.com.timeline.app.android.databinding.TimelineActivityBinding
import br.com.timeline.app.android.init.TimelineApplication
import br.com.timeline.app.android.view.adapters.*
import br.com.timeline.app.android.viewmodel.*
import java.util.*

class TimelineActivity : AppCompatActivity() {

    private lateinit var binding: TimelineActivityBinding

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TimelineApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TimelineActivityBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_TimelineAndroid)
        setContentView(binding.root)

        val recyclerAdapter = TaskRecyclerViewAdapter()
        val recyclerLayoutManager: RecyclerView.LayoutManager
        recyclerLayoutManager = LinearLayoutManager(this)

        binding.rvListTasks.layoutManager = recyclerLayoutManager
        binding.rvListTasks.adapter = recyclerAdapter

        setSupportActionBar(findViewById(R.id.toolbar))

        taskViewModel.allTasks.observe(this) { tasks ->
            tasks.let { recyclerAdapter.submitList(it) }
            val params = binding.appBar.layoutParams
            val layoutSummary: LinearLayout = findViewById(R.id.layout_view_summary)
            if (tasks.isEmpty()) {
                binding.layoutEmptyList.viewEmptyList.visibility = View.VISIBLE
                layoutSummary.visibility = View.INVISIBLE
                params.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    100.toFloat(),
                    resources.displayMetrics
                ).toInt()
            } else {
                binding.layoutEmptyList.viewEmptyList.visibility = View.INVISIBLE
                layoutSummary.visibility = View.VISIBLE
                params.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    160.toFloat(),
                    resources.displayMetrics
                ).toInt()
            }
        }

        initViewAndAddViewModelListener()

        binding.fab.setOnClickListener { showDialog(taskViewModel) }

        ImageViewCompat.setImageTintList(
            binding.fab,
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)
        ))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actions_tasks, menu)

        menu?.apply {
            for(index in 0 until this.size()){
                val item = this.getItem(index)
                val s = SpannableString(item.title)
                s.setSpan(ForegroundColorSpan(Color.BLACK),0,s.length,0)
                item.title = s
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear_tasks -> {
                taskViewModel.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog(
        taskViewModel: TaskViewModel
    ) = AddTaskDialog(
        taskViewModel
    ).show(supportFragmentManager, AddTaskDialog.TAG)

    private fun initViewAndAddViewModelListener() {
        val txtVisits = findViewById<TextView>(R.id.txt_visits)
        val txtCalls = findViewById<TextView>(R.id.txt_calls)
        val txtMails = findViewById<TextView>(R.id.txt_mails)
        val txtProposal = findViewById<TextView>(R.id.txt_proposal)
        val txtReunion = findViewById<TextView>(R.id.txt_reunion)
        val txtMore = findViewById<TextView>(R.id.txt_more)

        val layoutViewVisits = findViewById<LinearLayout>(R.id.layoutViewVisits)
        val layoutViewCalls = findViewById<LinearLayout>(R.id.layoutViewCalls)
        val layoutViewMails = findViewById<LinearLayout>(R.id.layoutViewMails)
        val layoutViewProposal = findViewById<LinearLayout>(R.id.layoutViewProposal)
        val layoutViewReunion = findViewById<LinearLayout>(R.id.layoutViewReunion)
        val layoutViewMore = findViewById<LinearLayout>(R.id.layoutViewMore)

        taskViewModel.countVisits.observe(this) {
            count -> setTaskSize(txtVisits, layoutViewVisits, count)
        }

        taskViewModel.countCalls.observe(this) {
            count -> setTaskSize(txtCalls, layoutViewCalls, count)
        }

        taskViewModel.countMails.observe(this) {
            count ->  setTaskSize(txtMails, layoutViewMails, count)
        }

        taskViewModel.countProposals.observe(this) {
            count -> setTaskSize(txtProposal, layoutViewProposal, count)
        }

        taskViewModel.countReunion.observe(this) {
            count -> setTaskSize(txtReunion, layoutViewReunion, count)
        }

        taskViewModel.countMore.observe(this) {
            count -> setTaskSize(txtMore, layoutViewMore, count)
        }
    }

    private fun setTaskSize(txtView: TextView, layout: LinearLayout, size: Int) {
        if (size > 0) {
            txtView.text = size.toString()
            layout.visibility = View.VISIBLE
            val params = layout.layoutParams
            params.width = LinearLayout.LayoutParams.WRAP_CONTENT
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
        } else {
            layout.visibility = View.INVISIBLE
            val params = layout.layoutParams
            params.width = 0
            params.height = 0
        }
    }
}