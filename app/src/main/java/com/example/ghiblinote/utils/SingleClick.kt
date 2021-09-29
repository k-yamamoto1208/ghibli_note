package com.example.ghiblinote.utils

import android.view.View

/**
 * ボタンの連打防止共通処理
 */
interface SingleClick {

    fun View.setOnSingleClickListener(listener: () -> Unit) {
        // 1秒間の待機時間を作る
        val delayMillis = 1000
        var pushedAt = 0L
        setOnClickListener {
            if (System.currentTimeMillis() - pushedAt < delayMillis) return@setOnClickListener
            pushedAt = System.currentTimeMillis()
            listener()
        }
    }
}