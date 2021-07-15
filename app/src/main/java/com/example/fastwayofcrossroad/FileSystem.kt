package com.example.fastwayofcrossroad

import java.io.*

class FileSystem (val filepath : String) {
    var filePath : String = ""
    val file : File = File(filepath)
    init {
        if(filePath == "")
            filePath = filepath
    }

    fun readFile() : CrossRoad? {
        val file = File(filePath)

        if(!file.exists())
            return null

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp = ""
        var counter = 0
        var list_counter = 0
        var name : String? = null
        var time : Int? = null
        var roadnums = Array<Int>(4) {0}
        var crosswalknums = Array<Int>(4) {0}

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

        return  CrossRoad(temp, time, roadnums, crosswalknums)
    }

    fun writeFile (crossRoad : CrossRoad?) : Boolean {
        if(crossRoad == null)
            return false

        val file = File(filePath)
        if(!file.exists())
            return false

        val writer = FileWriter(filePath)
        val buffer = BufferedWriter(writer)

        var string = ""
        string = crossRoad.name + '\n'
        string += crossRoad.time.toString()
        string += '\n'
        for(index in 0..3) {
            string += crossRoad.roadnums[index]
            string += '\n'
        }
        for(index in 0..3) {
            string += crossRoad.crosswalknums[index]
            string += '\n'
        }

        buffer.write(string)
        buffer.close()

        return true
    }
}