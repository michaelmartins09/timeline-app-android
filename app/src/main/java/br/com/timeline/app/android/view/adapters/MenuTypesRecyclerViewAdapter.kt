package br.com.timeline.app.android.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.timeline.app.android.R
import br.com.timeline.app.android.entities.MenuType
import br.com.timeline.app.android.utils.TaskType
import br.com.timeline.app.android.viewmodel.TaskViewModel
import kotlinx.coroutines.selects.select
import kotlin.collections.ArrayList

class MenuTypesRecyclerViewAdapter(
    private val context: Context,
    private val listMenuTypes: ArrayList<MenuType>,
    private val taskViewModel: TaskViewModel
) : RecyclerView.Adapter<MenuTypesRecyclerViewAdapter.ViewHolder>() {

    private var typeSelected = TaskType.NONE

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgMenuType: ImageView = view.findViewById(R.id.img_menu_type)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_menu_types, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuType = listMenuTypes[position]

        val menuSelected = menuType.type == typeSelected

        holder.imgMenuType.setImageResource(menuType.res)

        val resourceColor: Int = if (menuSelected) R.color.secondary_dark else R.color.grey
        val resourceColorTint: Int = if (menuSelected) R.color.secondary else R.color.grey_light2
        ImageViewCompat.setImageTintList(
            holder.imgMenuType,
            ColorStateList.valueOf(ContextCompat.getColor(context, resourceColor)
        ))

        holder.imgMenuType.backgroundTintList = ContextCompat.getColorStateList(context, resourceColorTint)

        holder.imgMenuType.setOnClickListener {
            typeSelected = menuType.type
            taskViewModel.lastTaskTypeSelected.value = typeSelected
            notifyDataSetChanged()
        }

    }

    override fun getItemCount() = listMenuTypes.size

}