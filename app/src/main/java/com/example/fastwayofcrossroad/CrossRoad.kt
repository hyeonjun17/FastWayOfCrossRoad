package com.example.fastwayofcrossroad

import android.os.Parcelable


data class CrossRoad (var name : String, var time : Int, var roadnums : Array<Int>,
                      var crosswalknums : Array<Int>)