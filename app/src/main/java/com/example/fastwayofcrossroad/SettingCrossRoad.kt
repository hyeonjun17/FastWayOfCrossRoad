package com.example.fastwayofcrossroad

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    fun checkNum (num : Int) : Boolean {
        return !(num < 1 || num > 4)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nameEditText.addTextChangedListener {
            name = it.toString()
            Log.d("debug", "$name")
        }

        binding.timeEditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                time = Integer.parseInt(it.toString())
                Log.d("debug", "$time")
            }
        }

        binding.crossroad1EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crossroad1 = Integer.parseInt(it.toString())
                Log.d("debug", "$crossroad1")
            }
        }

        binding.crossroad2EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crossroad2 = Integer.parseInt(it.toString())
                Log.d("debug", "$crossroad2")
            }
        }

        binding.crossroad3EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crossroad3 = Integer.parseInt(it.toString())
                Log.d("debug", "$crossroad3")
            }
        }

        binding.crossroad4EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crossroad4 = Integer.parseInt(it.toString())
                Log.d("debug", "$crossroad4")
            }
        }

        binding.crosswalk1EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crosswalk1 = Integer.parseInt(it.toString())
                Log.d("debug", "$crosswalk1")
            }
        }

        binding.crosswalk2EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crosswalk2 = Integer.parseInt(it.toString())
                Log.d("debug", "$crosswalk2")
            }
        }

        binding.crosswalk3EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crosswalk3 = Integer.parseInt(it.toString())
                Log.d("debug", "$crosswalk3")
            }
        }

        binding.crosswalk4EditText.addTextChangedListener {
            if(it.toString().toIntOrNull() != null) {
                crosswalk4 = Integer.parseInt(it.toString())
                Log.d("debug", "$crosswalk4")
            }
        }

        binding.completeButton.setOnClickListener {
            if(name != null && time != null && crossroad1 != null && crossroad2 != null && crossroad3 != null && crossroad4 != null && crosswalk1 != null
                && crosswalk2 != null && crosswalk3 != null && crosswalk4 != null) {
                    Log.d("debug", "variables are not null")
                if(checkNum(crossroad1!!) && checkNum(crossroad2!!) && checkNum(crossroad3!!) && checkNum(crossroad4!!) && checkNum(crosswalk1!!) && checkNum(crosswalk2!!)
                     && checkNum(crosswalk3!!) && checkNum(crosswalk4!!)) {
                         Log.d("debug", "making Intent..")
                    val intent = Intent(this, MainActivity::class.java)
                    Log.d("debug", "Intent made")
                    intent.putExtra("name", name!!)
                    intent.putExtra("time", time!!)
                    intent.putExtra("crossroad1", crossroad1!!)
                    intent.putExtra("crossroad2", crossroad2!!)
                    intent.putExtra("crossroad3", crossroad3!!)
                    intent.putExtra("crossroad4", crossroad4!!)
                    intent.putExtra("crosswalk1", crosswalk1!!)
                    intent.putExtra("crosswalk2", crosswalk2!!)
                    intent.putExtra("crosswalk3", crosswalk3!!)
                    intent.putExtra("crosswalk4", crosswalk4!!)
                    Log.d("debug", "putExtras")
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Input number must be 0 < num < 5", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Make sure that you input every number", Toast.LENGTH_LONG).show()
            }
        }
    }
}
