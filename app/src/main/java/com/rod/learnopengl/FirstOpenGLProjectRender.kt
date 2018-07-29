package com.rod.learnopengl

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * @author Rod
 * @date 2018/7/29
 */
class FirstOpenGLProjectRender : GLSurfaceView.Renderer {

    /**
     * 当绘制一帧时被调用
     * 在这个方法中一定要绘制一些东西，即使只是清空屏幕。否则可能会出现闪烁的情况
     */
    override fun onDrawFrame(gl: GL10?) {
        // 擦除屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充屏幕
        glClear(GL_COLOR_BUFFER_BIT)
    }

    /**
     * 每次Surface尺寸变化时被调用，如切换横竖屏
     */
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        // 设置视口尺寸，告诉OpenGL可以用来渲染surface的大小
        glViewport(0, 0, width, height)
    }

    /**
     * 当SurfaceView 被创建或者设备被唤醒，或者从其它Activity切换回来时，都可能被调用
     */
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 设置清空屏幕用的颜色
        glClearColor(1F, 0F, 0F, 0F)
    }
}