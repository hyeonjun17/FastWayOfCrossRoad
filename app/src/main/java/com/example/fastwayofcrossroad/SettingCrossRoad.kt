package com.example.fastwayofcrossroad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.fastwayofcrossroad.databinding.ActivitySettingCrossRoadBinding

class SettingCrossRoad : AppCompatActivity() {
    val binding by lazy {ActivitySettingCrossRoadBinding.inflate(layoutInflater)}
    var name : String? = null
    var time : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nameEditText.addTextChangedListener {
            name = it.toString()
            Log.d("debug", "$name")
        }

        binding.timeEditText.addTextChangedListener {
            time = Integer.parseInt(it.toString())
            Log.d("debug", "$time")
        }

        binding.completeButton.setOnClickListener {
            finish()
        }
    }
}