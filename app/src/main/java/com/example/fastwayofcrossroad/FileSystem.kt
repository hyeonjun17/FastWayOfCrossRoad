package com.example.fastwayofcrossroad

import java.io.*

class FileSystem (val filepath : String) {
    var filePath : String? = null
    val file : File = File(filepath)
    init {
        if(filePath == null)
            filePath = filePath
    }

    fun readFile() : List<CrossRoad>? {
        val file = File(filePath)
        var crossRoadsList = mutableListOf<CrossRoad>()

        if(!file.exists())
            return null

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp = ""
        var counter = 0
        var list_counter = 0
        var name : String? = null
        var roadnums = IntArray(4)
        var crosswalknums = IntArray(4)

        while(true) {
            counter++
            temp = buffer.readLine()
            if(temp == null)	break;
            else {
                if(counter == 1)
                    name = temp
                else if(counter >= 2 && counter <= 5)
                    roadnums[counter - 2] = temp.toInt()
                else if(counter >= 6 && counter <= 9)
                    crosswalknums[counter - 6] = temp.toInt()
                else
                {
                    val crossroad = CrossRoad(temp, roadnums, crosswalknums)
                    crossRoadsList.add(++list_counter, crossroad)
                    counter = 0
                }
            }
        }

        return crossRoadsList
    }

    fun writeFile (crossRoadsList : List<CrossRoad>?) : Boolean {
        if(crossRoadsList == null)
            return false

        val file = File(filePath)
        if(!file.exists())
            return false

        val writer = FileWriter(filePath)
        val buffer = BufferedWriter(writer)

        var string = ""
        for(index in 1..crossRoadsList.size) {
            if(crossRoadsList[index] != null) {
                string = crossRoadsList[index].name + '\n'
                for(_index in 0..3) {
                    string = string + crossRoadsList[index].roadnums[_index] + '\n'
                }
                for(_index in 0..3) {
                    string = string + crossRoadsList[index].crosswalknums[_index] + '\n'
                }
            }
        }

        buffer.write(string)
        buffer.close()
        return true
    }
}