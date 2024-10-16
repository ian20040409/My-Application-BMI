package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Receive data from the intent
        val name = intent.getStringExtra("NAME") ?: "訪客"
        val gender = intent.getStringExtra("GENDER") ?: "未知性別"
        val height = intent.getIntExtra("HEIGHT", 0)
        val weight = intent.getIntExtra("WEIGHT", 0)

        // Calculate the BMI
        val bmi = calculateBMI(weight, height)

        // Display the BMI result
        val resultTextView = findViewById<TextView>(R.id.tv_bmi_result)
        resultTextView.text = "$name $gender\n您的BMI值: $bmi"

        // Set up the return button
        val returnButton = findViewById<Button>(R.id.btn_return)
        returnButton.setOnClickListener {
            // Return the BMI value to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("BMI_RESULT", bmi)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Closes SecondActivity and returns to MainActivity
        }
    }

    private fun calculateBMI(weight: Int, height: Int): Double {
        return weight / (height / 100.0 * height / 100.0)
    }
}
