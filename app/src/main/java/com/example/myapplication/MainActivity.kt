package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_BMI = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.ed_name)
        val heightEditText = findViewById<EditText>(R.id.ed_h)
        val weightEditText = findViewById<EditText>(R.id.ed_w)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.radioGender)
        val calculateButton = findViewById<Button>(R.id.btn_BMI)
        val resultTextView = findViewById<TextView>(R.id.tv_result)

        calculateButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val height = heightEditText.text.toString().trim()
            val weight = weightEditText.text.toString().trim()

            // Check if name, height, or weight fields are empty
            if (name.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                resultTextView.text = "請輸入完整資訊。"
                return@setOnClickListener
            }

            val heightValue = height.toIntOrNull()
            val weightValue = weight.toIntOrNull()

            // Check if height and weight are valid numbers
            if (heightValue == null || weightValue == null) {
                resultTextView.text = "請輸入有效的身高和體重。"
                return@setOnClickListener
            }

            val selectedId = genderRadioGroup.checkedRadioButtonId
            val gender = if (selectedId == R.id.btn_m) "先生" else "女士"

            // Create an explicit intent to start SecondActivity
            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                putExtra("NAME", name)
                putExtra("GENDER", gender)
                putExtra("HEIGHT", heightValue)
                putExtra("WEIGHT", weightValue)
            }

            // Start SecondActivity and expect a result
            startActivityForResult(intent, REQUEST_CODE_BMI)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BMI && resultCode == Activity.RESULT_OK) {
            val bmi = data?.getDoubleExtra("BMI_RESULT", 0.0) ?: 0.0
            val bmiResult = getBMIResult(bmi)

            val nameEditText = findViewById<EditText>(R.id.ed_name)
            val genderRadioGroup = findViewById<RadioGroup>(R.id.radioGender)
            val resultTextView = findViewById<TextView>(R.id.tv_result)

            val name = nameEditText.text.toString()
            val selectedId = genderRadioGroup.checkedRadioButtonId
            val gender = if (selectedId == R.id.btn_m) "先生" else "女士"

            resultTextView.text = "$name $gender 您好\n" +
                    "您的BMI: $bmi\n" +
                    "建議: $bmiResult"
        }
    }

    private fun getBMIResult(bmi: Double): String {
        return when {
            bmi < 18.5 -> "體重過輕,增加健康的卡路里攝入"
            bmi in 18.5..24.9 -> "正常體重,繼續保持"
            bmi in 25.0..29.9 -> "過重,請多運動"
            else -> "肥胖,請多多運動"
        }
    }
}
