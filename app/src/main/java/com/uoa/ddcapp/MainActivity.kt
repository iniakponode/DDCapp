package com.uoa.ddcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uoa.ddcapp.databinding.ActivityCameraBinding
import com.uoa.ddcapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var viewBindingC: ActivityCameraBinding
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
    }
}