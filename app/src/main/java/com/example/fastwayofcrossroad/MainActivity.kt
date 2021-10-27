package com.example.fastwayofcrossroad

import android.annotation.SuppressLint
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastwayofcrossroad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var cr = CrossRoad("", 0, arrayOf(0), arrayOf(0))
    var openedRoad: Int = 0
    var openedCrosswalk : Int = 0
    var startPoint: Int = 0
    var endPoint: Int = 0

    val inc = 0
    val dec = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("debug", "MainActivity created")

        val fs = FileSystem("${filesDir.toString()}/CrossRoadData.txt")

        if(binding.road21.visibility == View.VISIBLE)
            binding.road21.visibility = View.INVISIBLE
        if(binding.road32.visibility == View.VISIBLE)
            binding.road32.visibility = View.INVISIBLE
        if(binding.road34.visibility == View.VISIBLE)
            binding.road34.visibility = View.INVISIBLE
        if(binding.road41.visibility == View.VISIBLE)
            binding.road41.visibility = View.INVISIBLE

        binding.loadButton.setOnClickListener {
            fs.readFile(cr)
            Toast.makeText(applicationContext, "Loaded Data", Toast.LENGTH_LONG).show()
        }

        binding.startpointRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> startPoint = 0
                R.id.radioButton2 -> startPoint = 1
                R.id.radioButton3 -> startPoint = 2
                R.id.radioButton4 -> startPoint = 3
            }
            Log.d("debug", "startPoint : $startPoint")
        }

        binding.endpointRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton1_ -> endPoint = 0
                R.id.radioButton2_ -> endPoint = 1
                R.id.radioButton3_ -> endPoint = 2
                R.id.radioButton4_ -> endPoint = 3
            }
            Log.d("debug", "endPoint : $endPoint")
        }

        binding.crossroadRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton1__ -> openedRoad = 0
                R.id.radioButton2__ -> openedRoad = 1
                R.id.radioButton3__ -> openedRoad = 2
                R.id.radioButton4__ -> openedRoad = 3
            }
            Log.d("debug", "openedRoad : $openedRoad")
            openedCrosswalk = 0
            for (i in 0..3) {
                if (cr.roadnums[openedRoad] == cr.crosswalknums[i]) {
                    openedCrosswalk = i
                    break
                }
            }
            Log.d("debug", "openedCrosswalk : $openedCrosswalk")
        }

        //cr = fs.readFile()

        makingCrossRoad()

        if(cr.name != "")
            binding.nameTextViewForVar.setText(cr.name.toString())
        else
            binding.nameTextViewForVar.setText("None")

        binding.calculateButton.setOnClickListener {
            Log.d("debug", "pressed calculate button")
            Log.d("debug", "startPoint : $startPoint")
            Log.d("debug", "endPoint : $endPoint")
            Log.d("debug", "openedRoad : $openedRoad")

            val zeroArr = arrayOf(0)
            if (cr.name == "" || cr.time == 0 || cr.crosswalknums.contentEquals(zeroArr)
                || cr.roadnums.contentEquals(zeroArr)
            ) {
            } else {
                val graph = Array<Array<Int>>(4) { Array<Int>(4) { Int.MAX_VALUE } }
                makingGraph(graph)
                for (i in 0..3) {
                    for (j in 0..3) {
                        Log.d("debug", "i : $i j : $j graph : ${graph[i][j]}")
                    }
                }
                if (!graph.contentEquals(arrayOf(arrayOf(0)))) {
                    val floydedGraph = Array<Array<Int>>(4){ Array<Int>(4) {Int.MAX_VALUE} }
                    for(i in 0..3) {
                        for(j in 0..3) {
                            floydedGraph[i][j] = graph[i][j]
                        }
                    }
                    floydWarshall(floydedGraph)
                    for (i in 0..3) {
                        for (j in 0..3) {
                            Log.d("debug", "i : $i j : $j floydedGraph : ${floydedGraph[i][j]}")
                        }
                    }
                    /*
                    if(startPoint == 1) {
                        if(endPoint == 0) {
                            if(floydedGraph[startPoint][endPoint] == graph[1][2] + graph[2][3] + graph[3][0]) {
                                shortRoadNum = 3
                                shortRoadList.add(Pair(1, 2))
                                shortRoadList.add(Pair(2, 3))
                                shortRoadList.add(Pair(3, 0))
                            } else if(floydedGraph[startPoint][endPoint] == graph[1][0]) {
                                shortRoadNum = 1
                                shortRoadList.add(Pair(1, 0))
                            }
                        }
                    } else if(starPoint)*/

                    var shortRoadNum = 0
                    val shortRoadList = mutableListOf<Pair<Int, Int>>()

                    var largePoint : Int; var smallPoint : Int
                    if(startPoint < endPoint) {
                        largePoint = endPoint
                        smallPoint = startPoint
                    } else {
                        largePoint = startPoint
                        smallPoint = endPoint
                    }

                    if(largePoint - smallPoint == 1) {
                        val largePoint_ = roadnumIncreaser(largePoint, 0, 3)
                        val largePoint__ = roadnumIncreaser(largePoint_, 0, 3)
                        if(floydedGraph[smallPoint][largePoint] == graph[largePoint][largePoint_] + graph[largePoint_][largePoint__] + graph[largePoint__][smallPoint]) {
                            shortRoadNum = 3
                            shortRoadList.add(Pair(largePoint, largePoint_))
                            shortRoadList.add(Pair(largePoint_, largePoint__))
                            shortRoadList.add(Pair(largePoint__, smallPoint))
                        } else if(floydedGraph[smallPoint][largePoint] == graph[smallPoint][largePoint]){
                            shortRoadNum = 1
                            shortRoadList.add(Pair(smallPoint, largePoint))
                        }
                    } else if(largePoint - smallPoint == 2) {
                        val largePoint_ = roadnumIncreaser(largePoint, 0, 3)
                        val smallPoint_ = roadnumIncreaser(smallPoint, 0, 3)
                        if(floydedGraph[smallPoint][largePoint] == graph[largePoint][largePoint_] + graph[largePoint_][smallPoint]) {
                            shortRoadNum = 2
                            shortRoadList.add(Pair(largePoint, largePoint_))
                            shortRoadList.add(Pair(largePoint_, smallPoint))
                        } else if(floydedGraph[smallPoint][largePoint] == graph[smallPoint][smallPoint_] + graph[smallPoint_][largePoint]) {
                            shortRoadNum = 2
                            shortRoadList.add(Pair(smallPoint, smallPoint_))
                            shortRoadList.add(Pair(smallPoint_, largePoint))
                        }
                    } else if(largePoint - smallPoint == 3) {
                        val smallPoint_ = roadnumIncreaser(smallPoint, 0, 3)
                        val smallPoint__ = roadnumIncreaser(smallPoint_, 0, 3)
                        if(floydedGraph[smallPoint][largePoint] == graph[smallPoint][smallPoint_] + graph[smallPoint_][smallPoint__] + graph[smallPoint__][largePoint]) {
                            shortRoadNum = 3
                            shortRoadList.add(Pair(smallPoint, smallPoint_))
                            shortRoadList.add(Pair(smallPoint_, smallPoint__))
                            shortRoadList.add(Pair(smallPoint__, largePoint))
                        } else if(floydedGraph[smallPoint][largePoint] == graph[smallPoint][largePoint]) {
                            shortRoadNum = 1
                            shortRoadList.add(Pair(smallPoint, largePoint))
                        }
                    }
                    /*
                    when (startPoint) {
                        0 -> {
                            when (endPoint) {
                                0 -> {
                                }
                                1 -> {
                                    if(floydedGraph[0][1] == graph[0][3] + graph[3][2] + graph[2][1]){
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(0, 3))
                                        shortRoadList.add(Pair(3, 2))
                                        shortRoadList.add(Pair(2, 1))
                                    } else if (floydedGraph[0][1] == graph[0][1]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(0, 1))
                                    }
                                }
                                2 -> {
                                    if (floydedGraph[0][2] == graph[0][1] + graph[1][2]) {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(0, 1))
                                        shortRoadList.add(Pair(1, 2))
                                    } else {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(0, 3))
                                        shortRoadList.add(Pair(3, 2))
                                    }
                                }
                                3 -> {
                                    if(floydedGraph[0][3] == graph[0][1] + graph[1][2] + graph[2][3]) {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(0, 1))
                                        shortRoadList.add(Pair(1, 2))
                                        shortRoadList.add(Pair(2, 3))
                                    } else if (floydedGraph[0][3] == graph[0][3]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(0, 3))
                                    }
                                }
                            }
                        }
                        1 -> {
                            when (endPoint) {
                                0 -> {
                                    if(floydedGraph[1][0] == graph[1][2] + graph[2][3] + graph[3][0]) {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(1, 2))
                                        shortRoadList.add(Pair(2, 3))
                                        shortRoadList.add(Pair(3, 0))
                                    } else if (floydedGraph[1][0] == graph[1][0]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(1, 0))
                                    }
                                }
                                1 -> {
                                }
                                2 -> {
                                    if(floydedGraph[1][2] == graph[1][0] + graph[0][3] + graph[3][2]) {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(1, 0))
                                        shortRoadList.add(Pair(0, 3))
                                        shortRoadList.add(Pair(3, 2))
                                    } else if (floydedGraph[1][2] == graph[1][2]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(1, 2))
                                    }
                                }
                                3 -> {
                                    if (floydedGraph[1][3] == graph[1][2] + graph[2][3]) {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(1, 2))
                                        shortRoadList.add(Pair(2, 3))
                                    } else {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(1, 0))
                                        shortRoadList.add(Pair(0, 3))
                                    }
                                }
                            }
                        }
                        2 -> {
                            when (endPoint) {
                                0 -> {
                                    if (floydedGraph[2][0] == graph[2][3] + graph[3][0]) {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(2, 3))
                                        shortRoadList.add(Pair(3, 0))
                                    } else {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(2, 1))
                                        shortRoadList.add(Pair(1, 0))
                                    }
                                }
                                1 -> {
                                    if (floydedGraph[2][1] == graph[2][1]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(2, 1))
                                    } else {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(2, 3))
                                        shortRoadList.add(Pair(3, 0))
                                        shortRoadList.add(Pair(0, 1))
                                    }
                                }
                                2 -> {
                                }
                                3 -> {
                                    if (floydedGraph[2][3] == graph[2][3]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(2, 3))
                                    } else {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(2, 1))
                                        shortRoadList.add(Pair(1, 0))
                                        shortRoadList.add(Pair(0, 3))
                                    }
                                }
                            }
                        }
                        3 -> {
                            when (endPoint) {
                                0 -> {
                                    if (floydedGraph[3][0] == graph[3][0]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(3, 0))
                                    } else {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(3, 2))
                                        shortRoadList.add(Pair(2, 1))
                                        shortRoadList.add(Pair(1, 0))
                                    }
                                }
                                1 -> {
                                    if (floydedGraph[3][1] == graph[3][0] + graph[0][1]) {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(3, 0))
                                        shortRoadList.add(Pair(0, 1))
                                    } else {
                                        shortRoadNum = 2
                                        shortRoadList.add(Pair(3, 2))
                                        shortRoadList.add(Pair(2, 1))
                                    }
                                }
                                2 -> {
                                    if (floydedGraph[3][2] == graph[3][2]) {
                                        shortRoadNum = 1
                                        shortRoadList.add(Pair(3, 2))
                                    } else {
                                        shortRoadNum = 3
                                        shortRoadList.add(Pair(3, 0))
                                        shortRoadList.add(Pair(0, 1))
                                        shortRoadList.add(Pair(1, 2))
                                    }
                                }
                                3 -> {
                                }
                            }
                        }
                    }*/
                    if (shortRoadNum == 0) {
                        Toast.makeText(
                            applicationContext,
                            "error : startpoint and endpoint is same",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Log.d("debug", "shortRoadNum : $shortRoadNum")
                        Log.d("debug", "shortRoadList : $shortRoadList")
                        Log.d("debug", "Total time is ${floydedGraph[startPoint][endPoint]}s")

                        if(binding.road21.visibility == View.VISIBLE)
                            binding.road21.visibility = View.INVISIBLE
                        if(binding.road32.visibility == View.VISIBLE)
                            binding.road32.visibility = View.INVISIBLE
                        if(binding.road34.visibility == View.VISIBLE)
                            binding.road34.visibility = View.INVISIBLE
                        if(binding.road41.visibility == View.VISIBLE)
                            binding.road41.visibility = View.INVISIBLE

                        for (i in 0 until shortRoadNum) {
                            if((shortRoadList[i].first == 0 && shortRoadList[i].second == 1) || (shortRoadList[i].first == 1 && shortRoadList[i].second == 0))
                                binding.road21.visibility = View.VISIBLE
                            else if((shortRoadList[i].first == 1 && shortRoadList[i].second == 2) || (shortRoadList[i].first == 2 && shortRoadList[i].second == 1))
                                binding.road32.visibility = View.VISIBLE
                            else if((shortRoadList[i].first == 2 && shortRoadList[i].second == 3) || (shortRoadList[i].first == 3 && shortRoadList[i].second == 2))
                                binding.road34.visibility = View.VISIBLE
                            else if((shortRoadList[i].first == 3 && shortRoadList[i].second == 0) || (shortRoadList[i].first == 0 && shortRoadList[i].second == 3))
                                binding.road41.visibility = View.VISIBLE
                        }

                        Toast.makeText(
                            applicationContext,
                            "Total time is ${floydedGraph[startPoint][endPoint]}s",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        binding.settingImageButton.setOnClickListener {
            val intent = Intent(this, SettingCrossRoad::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun makingCrossRoad() {
        if (intent.hasExtra("name")) {
            if (intent.getStringExtra("name") != null)
                cr.name = intent.getStringExtra("name")!!
            else
                cr.name = ""
            Log.d("debug", "name : ${cr.name}")
        }
        if (intent.hasExtra("time")) {
            cr.time = intent.getIntExtra("time", 0)
            Log.d("debug", "time : ${cr.time}")
        }
        if (intent.hasExtra("crossroad1") && intent.hasExtra("crossroad2")
            && intent.hasExtra("crossroad3") && intent.hasExtra("crossroad4")
        ) {
            cr.roadnums = arrayOf(
                intent.getIntExtra("crossroad1", 0),
                intent.getIntExtra("crossroad2", 0),
                intent.getIntExtra("crossroad3", 0),
                intent.getIntExtra("crossroad4", 0)
            )
            for (i in 0..3) {
                Log.d("debug", "roadnums : ${cr.roadnums[i]}")
            }
        }
        if (intent.hasExtra("crosswalk1") && intent.hasExtra("crosswalk2")
            && intent.hasExtra("crosswalk3") && intent.hasExtra("crosswalk4")
        ) {
            cr.crosswalknums = arrayOf(
                intent.getIntExtra("crosswalk1", 0),
                intent.getIntExtra("crosswalk2", 0),
                intent.getIntExtra("crosswalk3", 0),
                intent.getIntExtra("crosswalk4", 0)
            )
            for (i in 0..3) {
                Log.d("debug", "crosswalknums : ${cr.crosswalknums[i]}")
            }
        }
    }

    fun makingGraph(resultGraph : Array<Array<Int>>) {
        val zeroArr = arrayOf(0, 0, 0, 0)
        if (cr.name == "" || cr.time == 0 || cr.roadnums.contentEquals(zeroArr) || cr.crosswalknums.contentEquals(
                zeroArr
            )
        ) {
            for(i in 0..3) {
                for(j in 0..3) {
                    resultGraph[i][j] = 0
                }
            }
            return
        }

        //Log.d("debug", "openedCrosswalk = $openedCrosswalk")

        //var timecounter = cr.time

        //value of crosswalknums[] is crosswalk order, index value is road number

        Log.d("debug", "original cr.crosswalknums[openedCrosswalk] : ${cr.crosswalknums[openedCrosswalk]}")
        val new_crosswalknums = cr.crosswalknums.copyOf()
        while(new_crosswalknums[openedCrosswalk] != 1) {
            for(i in 0..3) {
                if(new_crosswalknums[i] == 4) {
                    new_crosswalknums[i] = 1
                } else {
                    new_crosswalknums[i]++
                }
            }
        }

        val crosswalkGraph = Array<Array<Int>>(4) { Array<Int>(4) { Int.MAX_VALUE } }

        crosswalkGraph[0][3] = new_crosswalknums[3]
        crosswalkGraph[3][0] = new_crosswalknums[3]
        for(i in 0..2) {
            crosswalkGraph[i + 1][i] = new_crosswalknums[i]
            crosswalkGraph[i][i + 1] = new_crosswalknums[i]
        }
        for(i in 0..3) {
            for(j in 0..3) {
                Log.d("debug", "i : $i j : $j crosswalkGraph[$i][$j] : ${crosswalkGraph[i][j]}")
            }
        }

        //now simulation divided into 2 ways, left, right

        //val resultGraph = Array<Array<Int>>(4) { Array<Int>(4) { Int.MAX_VALUE } }

        //left
        var previous_vertex = 0; var present_vertex = startPoint; var next_vertex = 0
        while(true) {
            if(present_vertex == 0) {
                previous_vertex = 3
            } else {
                previous_vertex = present_vertex - 1
            }
            if(present_vertex == 3) {
                next_vertex = 0
            } else {
                next_vertex = present_vertex + 1
            }
            val A = crosswalkGraph[previous_vertex][present_vertex]; val B = crosswalkGraph[present_vertex][next_vertex]
            if(present_vertex == startPoint) {
                resultGraph[present_vertex][next_vertex] = B * cr.time
                resultGraph[next_vertex][present_vertex] = B * cr.time
            } else if(A < B) {
                resultGraph[present_vertex][next_vertex] = (B - A) * cr.time
                resultGraph[next_vertex][present_vertex] = (B - A) * cr.time
            } else {
                resultGraph[present_vertex][next_vertex] = (B + 4 - A) * cr.time
                resultGraph[next_vertex][present_vertex] = (B + 4 - A) * cr.time
            }
            if(next_vertex == endPoint) {
                break
            }
            if(present_vertex == 3) {
                present_vertex = 0
            } else {
                present_vertex++
            }
        }
        //right
        previous_vertex = 0; present_vertex = startPoint; next_vertex = 0
        while(true) {
            if(present_vertex == 3) {
                previous_vertex = 0
            } else {
                previous_vertex = present_vertex + 1
            }
            if(present_vertex == 0) {
                next_vertex = 3
            } else {
                next_vertex = present_vertex - 1
            }
            val A = crosswalkGraph[previous_vertex][present_vertex]; val B = crosswalkGraph[present_vertex][next_vertex]
            if(present_vertex == startPoint) {
                resultGraph[present_vertex][next_vertex] = B * cr.time
                resultGraph[next_vertex][present_vertex] = B * cr.time
            } else if(A < B) {
                resultGraph[present_vertex][next_vertex] = (B - A) * cr.time
                resultGraph[next_vertex][present_vertex] = (B - A) * cr.time
            } else {
                resultGraph[present_vertex][next_vertex] = (B + 4 - A) * cr.time
                resultGraph[next_vertex][present_vertex] = (B + 4 - A) * cr.time
            }
            if(next_vertex == endPoint) {
                break
            }
            if(present_vertex == 0) {
                present_vertex = 3
            } else {
                present_vertex--
            }
        }

        //old and wrong algorithm
/*
        while (true) {
            if (startPoint == 0) {
                if (openedCrosswalk == startPoint || openedCrosswalk == startPoint + 3) {
                break
                }
            } else {
                if (openedCrosswalk == startPoint || openedCrosswalk == startPoint - 1) {
                    break
                }
            }
            timecounter += timecounter
            if (openedCrosswalk == 3)
                openedCrosswalk = 0
            else
                openedCrosswalk += 1
        }
        val startingCrossWalkPoint = openedCrosswalk
        var cycle = 0
        while (true) {
            if (startingCrossWalkPoint == openedCrosswalk)
                cycle += 1
            if (cycle == 2)
                break;
            if (openedCrosswalk == 3) {
                Graph[0][openedCrosswalk] = timecounter - cr.time
                Graph[openedCrosswalk][0] = timecounter - cr.time
            } else {
                Graph[openedCrosswalk][openedCrosswalk + 1] = timecounter - cr.time
                Graph[openedCrosswalk + 1][openedCrosswalk] = timecounter - cr.time
            }
            timecounter += cr.time
            if (openedCrosswalk == 3)
                openedCrosswalk = 0
            else
                openedCrosswalk += 1
        }*/

        for (i in 0..3) {
            for (j in 0..3) {
                Log.d("debug", "i : $i j : $j resultGraph[$i][$j] : ${resultGraph[i][j]}")
            }
        }
    }

    fun floydWarshall(G: Array<Array<Int>>) {
        for (k in 0..3) {
            for (i in 0..3) {
                for (j in 0..3) {
                    if(G[i][k] == Int.MAX_VALUE || G[k][j] == Int.MAX_VALUE || i == k || j == k || i == j) {
                        continue
                    } else if (G[i][k] + G[k][j] < G[i][j]) {
                        G[i][j] = G[i][k] + G[k][j]
                    }
                }
            }
        }
    }

    fun roadnumIncreaser(number : Int, min : Int, max : Int) : Int {
        var num = number
        num++
        if (num > max){
            num = min
        }
        return num
    }
}