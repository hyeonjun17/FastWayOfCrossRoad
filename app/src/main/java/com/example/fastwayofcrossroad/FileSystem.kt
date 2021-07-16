package com.example.fastwayofcrossroad

import android.util.Log
import java.io.*

class FileSystem (filepath : String) {
    private val file = File(filepath)

    fun readFile(cr : CrossRoad) {
        if(!file.exists())
            return

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp = ""
        var name = ""
        var time = 0
        val roadnums = Array<Int>(4) {0}
        val crosswalknums = Array<Int>(4) {0}

        temp = buffer.readLine()
        name = temp
        temp = buffer.readLine()
        time = temp.toInt()
        for(index in 0..3) {
            temp = buffer.readLine()
            roadnums[index] = temp.toInt()
        }
        for(index in 0..3) {
            temp = buffer.readLine()
            crosswalknums[index] = temp.toInt()
        }
        buffer.close()

        cr.name = name
        cr.time = time
        cr.roadnums = roadnums
        cr.crosswalknums = crosswalknums
        Log.d("debug", "read name : $name time : $time")
        for (i in 0..3) {
            Log.d("debug", "roadnums[$i] : ${roadnums[i]}")
        }
        for (i in 0..3) {
            Log.d("debug", "crosswalknums[$i] : ${crosswalknums[i]}")
        }
    }

    fun writeFile (cr : CrossRoad) {
        if(!file.exists()) {
            file.createNewFile()
        }

        val writer = FileWriter(file)
        val buffer = BufferedWriter(writer)

        buffer.append(cr.name)
        buffer.newLine()
        buffer.append(cr.time.toString())
        buffer.newLine()
        for (i in 0..3) {
            buffer.append(cr.roadnums[i].toString())
            buffer.newLine()
        }
        for (i in 0..3) {
            buffer.append(cr.crosswalknums[i].toString())
            buffer.newLine()
        }
        buffer.close()
    }
}