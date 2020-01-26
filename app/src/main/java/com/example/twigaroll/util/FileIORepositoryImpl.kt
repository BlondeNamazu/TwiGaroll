package com.example.twigaroll.util

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.twigaroll.R
import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileIORepositoryImpl @Inject constructor() : FileIORepository {

    override fun readFile(context: Context?): String {
        if(context==null) Log.d("Namazu","context is null")
        context ?: return ""
        val parentDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            context.getString(R.string.app_name)
        )
        val file = File(
            parentDir,
            context.getString(R.string.tweet_id_file_name)
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

    override fun writeFile(context: Context?, string: String) {
        if(context==null) Log.d("Namazu","context is null")
        context ?: return
        val parentDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            context.getString(R.string.app_name)
        )
        val file = File(
            parentDir,
            context.getString(R.string.tweet_id_file_name)
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