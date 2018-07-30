package com.rod.learnopengl.util

import android.opengl.GLES20.*
import android.util.Log

/**
 *
 * @author Rod
 * @date 2018/7/31
 */
class ShaderHelper {
    companion object {
        fun compileVertexShader(shaderCode: String) = compileShader(GL_VERTEX_SHADER, shaderCode)

        fun compileFragmentShader(shaderCode: String) = compileShader(GL_FRAGMENT_SHADER, shaderCode)

        fun compileShader(type: Int, shaderCode: String): Int {
            val shaderObjectId = glCreateShader(type)
            if (shaderObjectId == 0) {
                // 表示对象创建失败
                return 0;
            }
            // 把着色器源代码上传到着色器对象里
            glShaderSource(shaderObjectId, shaderCode)
            // 编译这个着色器
            glCompileShader(shaderObjectId)

            // 检查编译是失败还是成功
            val compileStatus = intArrayOf(0)
            // 告诉OpenGL读取与shaderObjectId关联的编译状态，并把它写入compileStatus的第0个元素
            glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)

            Log.d("ShaderHelper", "Results of compiling source:\n$shaderCode\n: ${glGetShaderInfoLog(shaderObjectId)}")

            if (compileStatus[0] == 0) {
                // 如果编译失败，删除shader对象
                glDeleteShader(shaderObjectId)
                return 0
            }
            return shaderObjectId
        }

        fun linkProgram(vertesShaderId: Int, fragmentShaderId: Int): Int {
            val programObjectId = glCreateProgram()
            if (programObjectId == 0) {
                return 0
            }

            glAttachShader(programObjectId, vertesShaderId)
            glAttachShader(programObjectId, fragmentShaderId)

            glLinkProgram(programObjectId)
            val linkStatus = intArrayOf(0)
            glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] == 0) {
                glDeleteProgram(programObjectId)
                return 0
            }
            return programObjectId
        }

        fun validateProgram(programObjectId: Int): Boolean {
            glValidateProgram(programObjectId)
            val validateStatus = intArrayOf(0)
            glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)

            Log.d("ShaderHelper", "Results of validating program: ${validateStatus[0]} \nLog: ${glGetProgramInfoLog(programObjectId)}")
            return validateStatus[0] != 0
        }
    }
}