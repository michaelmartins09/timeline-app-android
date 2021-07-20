package br.com.timeline.app.android.utils.mock

import br.com.timeline.app.android.R
import br.com.timeline.app.android.entities.MenuType
import br.com.timeline.app.android.utils.enums.TaskType

class ListMock {
    companion object {
        fun getMenuTypes(): ArrayList<MenuType> {
            val list = ArrayList<MenuType>()
            list.add(MenuType(TaskType.MAIL, R.drawable.ic_mail))
            list.add(MenuType(TaskType.CALL, R.drawable.ic_phone))
            list.add(MenuType(TaskType.PROPOSAL, R.drawable.ic_file_text))
            list.add(MenuType(TaskType.REUNION, R.drawable.ic_icon_briefcase))
            list.add(MenuType(TaskType.VISIT, R.drawable.ic_map_pin))
            list.add(MenuType(TaskType.MORE, R.drawable.ic_more))
            return list
        }
    }
}