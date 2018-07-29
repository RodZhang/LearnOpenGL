package com.rod.learnopengl

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onFirstOpenGLClick(view: View) {
        startActivity(Intent(this, FirstOpenGLProjectActivity::class.java))
    }
}
