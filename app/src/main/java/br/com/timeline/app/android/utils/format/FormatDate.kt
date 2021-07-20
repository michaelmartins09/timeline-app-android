package br.com.timeline.app.android.utils.format

import java.text.SimpleDateFormat
import java.util.*

class FormatDate {

    companion object {
        private const val timePattern = "HH:mm"
        private const val datePattern = "d MMM yyyy"
        private const val weekdayPattern = "EE"
        private val locatePtBr = Locale("pt", "BR")

        fun get(date: Date): String = SimpleDateFormat(datePattern, locatePtBr).format(date)

        fun getTime(date: Date): String = SimpleDateFormat(timePattern, locatePtBr).format(date)

        fun getWeekday(date: Date): String = SimpleDateFormat(weekdayPattern, locatePtBr).format(date)
    }


}