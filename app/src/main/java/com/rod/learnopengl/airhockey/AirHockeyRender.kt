package com.rod.learnopengl.airhockey

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.rod.learnopengl.R
import com.rod.learnopengl.util.ShaderHelper
import com.rod.learnopengl.util.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *
 * @author Rod
 * @date 2018/7/29
 */
class AirHockeyRender(val context: Context) : GLSurfaceView.Renderer {

    companion object {
        const val POSITION_COMMPONENT_COUNT = 2
        const val BYTES_PER_FLOAT = 4
        const val U_COLOR = "u_Color"
        const val A_POSITION = "a_Position"
    }

    private var vertexData: FloatBuffer? = null
    private val tableVerticesWithTriangles = floatArrayOf(
            // triangle 1
            -0.5F, -0.5F,
            0.5F, 0.5F,
            -0.5F, 0.5F,
            // triangle 2
            -0.5f, -0.5F,
            0.5F, -0.5F,
            0.5F, 0.5F,
            // line 1
            -0.5F, 0F,
            0.5F, 0F,
            // line 2
            0F, -0.25F,
            0F, 0.25F
    )

    private var programObjectId = 0
    private var uColorLocation = 0
    private var aPositionLocation = 0

    init {
        vertexData = ByteBuffer
                // 分配顶点内存
                .allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
                // 告诉字节缓冲区按照本地字节序组织内容，本地字节序是指：当一个值占用多个字节时，
                // 字节按照从最重要位到最不重要位，或者相反顺序排列
                // 知道这个排序并不重要，重要的是作为一个平台要使用同样的排序
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        // 把数据从Dalvik内存复制到本地内存
        vertexData!!.put(tableVerticesWithTriangles)
    }

    /**
     * 当绘制一帧时被调用
     * 在这个方法中一定要绘制一些东西，即使只是清空屏幕。否则可能会出现闪烁的情况
     */
    override fun onDrawFrame(gl: GL10?) {
        // 擦除屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充屏幕
        glClear(GL_COLOR_BUFFER_BIT)
        glUniform4f(uColorLocation, 1F, 1F, 1F, 1F)
        glDrawArrays(GL_TRIANGLES, 0, 6)

        glUniform4f(uColorLocation, 1F, 0F, 0F, 1F)
        glDrawArrays(GL_LINES, 6, 2)

        glUniform4f(uColorLocation, 0F, 0F, 1F, 1F)
        glDrawArrays(GL_POINTS, 8, 1)

        glUniform4f(uColorLocation, 1F, 0F, 0F, 1F)
        glDrawArrays(GL_POINTS, 9, 1)
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
        glClearColor(0F, 0F, 0F, 0F)
        val vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)

        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)

        programObjectId = ShaderHelper.linkProgram(vertexShader, fragmentShader)

        ShaderHelper.validateProgram(programObjectId)

        glUseProgram(programObjectId)

        uColorLocation = glGetUniformLocation(programObjectId, U_COLOR)
        aPositionLocation = glGetAttribLocation(programObjectId, A_POSITION)

        vertexData!!.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COMMPONENT_COUNT, GL_FLOAT, false, 0, vertexData)

        glEnableVertexAttribArray(aPositionLocation)
    }
}