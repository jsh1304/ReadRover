package com.jj.readrover

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // 해당 class가 Hilt를 사용하는 안드로이드 애플리케이션임을 나타냄
class ReaderApplication: Application() {
}