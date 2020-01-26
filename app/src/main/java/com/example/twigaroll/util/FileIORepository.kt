package com.example.twigaroll.util

import android.content.Context

interface FileIORepository {
    fun readFile(context: Context?): String
    fun writeFile(context: Context?, string: String)
}