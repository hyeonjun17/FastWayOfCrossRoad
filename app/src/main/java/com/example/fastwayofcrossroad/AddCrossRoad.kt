package com.example.fastwayofcrossroad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastwayofcrossroad.databinding.ActivityAddCrossRoadBinding

class AddCrossRoad : AppCompatActivity() {
    val binding by lazy {ActivityAddCrossRoadBinding.inflate(layoutInflater)}

    var name : String? = null
    var waitingSec : Int? = null
    val crossRoad = mutableListOf<Int>()
    val crossWalk = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.completeButton.setOnClickListener {
            finish()
        }
    }
}