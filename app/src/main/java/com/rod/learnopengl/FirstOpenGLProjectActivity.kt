package com.rod.learnopengl

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Toast

/**
 *
 * @author Rod
 * @date 2018/7/29
 */
class FirstOpenGLProjectActivity : Activity() {
    private lateinit var mGlSurfaceView: GLSurfaceView
    private var mRendererSet = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGlSurfaceView = GLSurfaceView(this)

        // 判断是否支持OpenGL ES 2.0
        val supportsEs2 = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).deviceConfigurationInfo.reqGlEsVersion >= 0x20000
        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context
            mGlSurfaceView.setEGLContextClientVersion(2)
            mGlSurfaceView.setRenderer(FirstOpenGLProjectRender())
            mRendererSet = true
        } else {
            Toast.makeText(this, "Do not support OpenGL ES 2.0", Toast.LENGTH_SHORT).show()
            return
        }
        setContentView(mGlSurfaceView)
    }

    override fun onResume() {
        super.onResume()
        if (mRendererSet) {
            mGlSurfaceView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mRendererSet) {
            mGlSurfaceView.onPause()
        }
    }
}