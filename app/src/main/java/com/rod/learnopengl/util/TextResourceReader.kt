package com.rod.learnopengl.util

import android.content.Context
import android.support.annotation.RawRes
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *
 * @author Rod
 * @date 2018/7/31
 */
object TextResourceReader {

    fun readTextFileFromResource(context: Context, @RawRes resourceId: Int): String {
        val body = StringBuilder()

        val inputStream = context.resources.openRawResource(resourceId)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        var nextLine = bufferedReader.readLine()
        while (nextLine != null) {
            body.append(nextLine)
            body.append("\n")
            nextLine = bufferedReader.readLine()
        }
        return body.toString()
    }
}