package com.example.ghiblinote

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hiltのコード生成をトリガーするアノテーション
@HiltAndroidApp
class GhibliNoteApplication : Application()