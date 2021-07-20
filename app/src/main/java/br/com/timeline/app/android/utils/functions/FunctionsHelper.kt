package br.com.timeline.app.android.utils.functions

import br.com.timeline.app.android.R
import br.com.timeline.app.android.utils.enums.TaskType

class FunctionsHelper {
    companion object {
        fun getIconByTaskType(type: TaskType) = when(type) {
            TaskType.CALL -> R.drawable.ic_phone
            TaskType.MAIL -> R.drawable.ic_mail
            TaskType.VISIT -> R.drawable.ic_map_pin
            TaskType.NONE -> R.drawable.ic_more
            TaskType.REUNION -> R.drawable.ic_icon_briefcase
            TaskType.PROPOSAL -> R.drawable.ic_file_text
            TaskType.MORE -> R.drawable.ic_more
        }
    }
}