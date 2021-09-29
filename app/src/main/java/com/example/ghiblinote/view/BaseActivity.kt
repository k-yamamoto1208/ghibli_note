package com.example.ghiblinote.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.ghiblinote.R
import com.example.ghiblinote.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_base.*

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    /** ViewBinding用のbindingクラスをlateinit varで定義する */
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // bindingクラスのインスタンスを生成し、setContentViewで画面上のアクティブビューにする
        binding = ActivityBaseBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // BottomNavigationViewにNavControllerをセット
        binding.bottomNavigation.setupWithNavController(
            Navigation.findNavController(
                this@BaseActivity,
                R.id.nav_host_fragment_container
            )
        )
    }
}