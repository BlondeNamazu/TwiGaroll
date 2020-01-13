package com.example.twigaroll.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*

object FileIO {

    val PARENT_DIR = "TwiGaroll"
    val FILE_NAME = "tweetIDs"

    fun readFile(context: Context?): String {
        if(context==null) Log.d("Namazu","context is null")
        context ?: return ""
        val parentDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            PARENT_DIR
        )
        val file = File(
            parentDir,
            FILE_NAME
        )
        if (!file.exists()) return ""
        return if (isExternalStorageReadable(file)) {
            FileInputStream(file)
                .bufferedReader()
                .use {
                    it.readText()
                }
        } else ""
    }

    fun writeFile(context: Context?, string: String) {
        if(context==null) Log.d("Namazu","context is null")
        context ?: return
        val parentDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            PARENT_DIR
        )
        val file = File(
            parentDir,
            FILE_NAME
        )
        if(!file.exists()) {
            parentDir.mkdirs()
            file.createNewFile()
        }
        if (isExternalStorageWritable(file)) {
            FileOutputStream(file)
                .bufferedWriter()
                .use {
                    it.write(string)
                }
        }else{
            Log.d("Namazu","file is not Writable")
        }
    }

    private fun isExternalStorageWritable(file: File): Boolean {
        val state = Environment.getExternalStorageState(file)
        return Environment.MEDIA_MOUNTED == state
    }

    private fun isExternalStorageReadable(file: File): Boolean {
        val state = Environment.getExternalStorageState(file)
        return Environment.MEDIA_MOUNTED == state
    }
}