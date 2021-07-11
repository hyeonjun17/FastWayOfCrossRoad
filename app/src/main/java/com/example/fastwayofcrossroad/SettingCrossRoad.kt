package com.example.fastwayofcrossroad

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.fastwayofcrossroad.databinding.ActivitySettingCrossRoadBinding

class SettingCrossRoad : AppCompatActivity() {
    val binding by lazy {ActivitySettingCrossRoadBinding.inflate(layoutInflater)}
    var name : String? = null
    var time : Int? = null
    var crossroad1 : Int? = null
    var crossroad2 : Int? = null
    var crossroad3 : Int? = null
    var crossroad4 : Int? = null
    var crosswalk1 : Int? = null
    var crosswalk2 : Int? = null
    var crosswalk3 : Int? = null
    var crosswalk4 : Int? = null
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

        binding.crossroad1EditText.addTextChangedListener {
            crossroad1 = Integer.parseInt(it.toString())
            Log.d("debug", "$crossroad1")
        }

        binding.crossroad2EditText.addTextChangedListener {
            crossroad2 = Integer.parseInt(it.toString())
            Log.d("debug", "$crossroad2")
        }

        binding.crossroad3EditText.addTextChangedListener {
            crossroad3 = Integer.parseInt(it.toString())
            Log.d("debug", "$crossroad3")
        }

        binding.crossroad4EditText.addTextChangedListener {
            crossroad4 = Integer.parseInt(it.toString())
            Log.d("debug", "$crossroad4")
        }

        binding.crosswalk1EditText.addTextChangedListener {
            crosswalk1 = Integer.parseInt(it.toString())
            Log.d("debug", "$crosswalk1")
        }

        binding.crosswalk2EditText.addTextChangedListener {
            crosswalk2 = Integer.parseInt(it.toString())
            Log.d("debug", "$crosswalk2")
        }

        binding.crosswalk3EditText.addTextChangedListener {
            crosswalk3 = Integer.parseInt(it.toString())
            Log.d("debug", "$crosswalk3")
        }

        binding.crosswalk4EditText.addTextChangedListener {
            crosswalk4 = Integer.parseInt(it.toString())
            Log.d("debug", "$crosswalk4")
        }

        binding.completeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("name", name)
            intent.putExtra("time", time)
            intent.putExtra("crossroad1", crossroad1)
            intent.putExtra("crossroad2", crossroad2)
            intent.putExtra("crossroad3", crossroad3)
            intent.putExtra("crossroad4", crossroad4)
            intent.putExtra("crosswalk1", crosswalk1)
            intent.putExtra("crosswalk2", crosswalk2)
            intent.putExtra("crosswalk3", crosswalk3)
            intent.putExtra("crosswalk4", crosswalk4)

            startActivity(intent)
            finish()
        }
    }
}
