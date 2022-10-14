package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tvOutput : TextView? = null
    var lastNumeric = false // This variable is used to check if this is the last number
    var lastDot = false // Used to check if the dot is already in use

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOutput = findViewById(R.id.tvOutput)
    }

    fun onDigit (view : View) {
        tvOutput?.append((view as Button).text) // Captures the text content of the View to be used in the app
        lastNumeric = true
        lastDot = false
    }

    fun onClear (view : View) {
        tvOutput?.text = "" // Clears the screen by setting its content to an empty string
    }

    fun onDecimalPoint(view : View) { // This function allow us to add the decimal point, avoid that it's repeated and also keep adding numbers after the point
        if (lastNumeric && !lastDot) {
            tvOutput?.append(("."))
            lastNumeric = false
            lastDot = true
        }
    }

    private fun isOperatorAdded (value : String) : Boolean {
        return if (value.startsWith("-")) {
            false
        }
        else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    fun onOperator (view : View) { // This function checks if its possible to add an operator
        tvOutput?.text?.let { // The function will only run if both aren't null.
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvOutput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual (view : View) {
        if (lastNumeric) {
            var tvValue = tvOutput?.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-") // This value is used to split the string in two using the minus signal as mark
                    var first = splitValue[0] // This variable is the first value of the calculation.
                    var second = splitValue[1] // This variable is the second value of the calculation.

                    if (prefix.isNotEmpty()) {
                        first = prefix + first // Adds a negative signal to the first operator if needed
                    }
                    tvOutput?.text = removeZeroAfterDot((first.toDouble() - second.toDouble()).toString()) // Returns the result to the display.
                }
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+") // This value is used to split the string in two using the minus signal as mark
                    var first = splitValue[0] // This variable is the first value of the calculation.
                    var second = splitValue[1] // This variable is the second value of the calculation.

                    if (prefix.isNotEmpty()) {
                        first = prefix + first // Adds a negative signal to the first operator if needed
                    }
                    tvOutput?.text = removeZeroAfterDot((first.toDouble() + second.toDouble()).toString()) // Returns the result to the display.
                }
                else if (tvValue.contains("X")) {
                    val splitValue = tvValue.split("X") // This value is used to split the string in two using the minus signal as mark
                    var first = splitValue[0] // This variable is the first value of the calculation.
                    var second = splitValue[1] // This variable is the second value of the calculation.

                    if (prefix.isNotEmpty()) {
                        first = prefix + first // Adds a negative signal to the first operator if needed
                    }
                    tvOutput?.text = removeZeroAfterDot((first.toDouble() * second.toDouble()).toString()) // Returns the result to the display.
                }
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/") // This value is used to split the string in two using the minus signal as mark
                    var first = splitValue[0] // This variable is the first value of the calculation.
                    var second = splitValue[1] // This variable is the second value of the calculation.

                    if (prefix.isNotEmpty()) {
                        first = prefix + first // Adds a negative signal to the first operator if needed
                    }
                    tvOutput?.text = removeZeroAfterDot((first.toDouble() / second.toDouble()).toString()) // Returns the result to the display.
                }


            }
            catch (e : ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot (result : String) : String {
        var value = result

        if (result.contains(".0"))
            value = result.substring(0, result.length - 2) // The final '.0' will be the substring we intend to remove

        return value
    }
}