package com.example.b2012046_calculator
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    private var tvInput: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View) {
        val digit = (view as TextView).text.toString()
        Log.d("Tag", digit)
        if (lastNumeric && !isOperatorAdded(tvInput?.text.toString())) {
            tvInput?.append(digit)
        }else {
            tvInput?.append(digit)
        }
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }
    fun onEqual(view: View) {
        if (lastNumeric) {
            // Lấy chuỗi nhập từ TextView
            val inputValue = tvInput?.text.toString()
            var result = ""
            val isNegative = inputValue.startsWith("-") // -3
            val negMultiplier = if (isNegative) -1 else 1
            val cleanedInput = if (isNegative) inputValue.substring(1) else inputValue // 3
            Log.d("log1", inputValue)  //-3-3
            Log.d("log2", inputValue.substring(1) ) // 3-3
            try {
                val operators = listOf("+", "-", "*", "/")
                val operator = operators.find { cleanedInput.contains(it) }
                Log.d("log3", operator.toString())
                if (operator != null) {
                    val splitValue = cleanedInput.split(operator)
                    val operand1 = negMultiplier * splitValue[0].toDouble()
                    val operand2 = splitValue[1].toDouble()
                    result = when (operator) {
                        "+" -> (operand1 + operand2).toString()
                        "-" -> (operand1 - operand2).toString()
                        "*" -> (operand1 * operand2).toString()
                        "/" -> if (operand2 != 0.0) (operand1 / operand2).toString() else "Error"
                        else -> ""
                    }
                }
                result = removeZeroAfterDot(result)
                tvInput?.text = result
            } catch (e: ArithmeticException) {
                tvInput?.text = "Error"
            }
            lastNumeric = true
        }
    }



    fun onOperator(view: View) {
        val operator = (view as TextView).text
        Log.d("lognnnnnnn", operator.toString())
//        if (lastNumeric || operator == "-" && tvInput?.text.toString().isEmpty()) {
        if (lastNumeric && !isOperatorAdded(tvInput?.text.toString())){
            tvInput?.append(operator)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append((view as TextView).text)
            lastNumeric = false
            lastDot = true
        }
    }
//    private fun isOperatorAdded(value: String): Boolean {
//        return value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
//    }
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (value.contains(".")) {
            value = value.replace("0*$".toRegex(), "")
            if (value.endsWith(".")) {
                value = value.substring(0, value.length - 1)
            }
        }
        return value
    }
}
