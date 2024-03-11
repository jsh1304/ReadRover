package com.jj.readrover.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp
import java.util.*


fun formatDate(timestamp: Timestamp): String {
    // 날짜를 변환하고, 요일을 제거
    return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.KOREAN)
        .format(timestamp.toDate())
        .toString().split(",")[0]
}
