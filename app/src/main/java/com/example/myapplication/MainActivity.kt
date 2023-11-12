package com.example.myapplication

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

    fun onDigit(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput?.text.toString())) {
            tvInput?.append((view as TextView).text)
        } else if (tvInput?.text.toString() == "0") {
            tvInput?.text = (view as TextView).text
        } else {
            tvInput?.append((view as TextView).text)
        }
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var inputValue = tvInput?.text.toString()
            var result = ""

            try {
                if (inputValue.contains("+")) {
                    val splitValue = inputValue.split("+")
                    val operand1 = splitValue[0].toDouble()
                    val operand2 = splitValue[1].toDouble()
                    result = (operand1 + operand2).toString()
                } else if (inputValue.contains("-")) {
                    val splitValue = inputValue.split("-")
                    val operand1 = splitValue[0].toDouble()
                    val operand2 = splitValue[1].toDouble()
                    result = (operand1 - operand2).toString()
                } else if (inputValue.contains("*")) {
                    val splitValue = inputValue.split("*")
                    val operand1 = splitValue[0].toDouble()
                    val operand2 = splitValue[1].toDouble()
                    result = (operand1 * operand2).toString()
                } else if (inputValue.contains("/")) {
                    val splitValue = inputValue.split("/")
                    val operand1 = splitValue[0].toDouble()
                    val operand2 = splitValue[1].toDouble()
                    if (operand2 != 0.0) {
                        result = (operand1 / operand2).toString()
                    } else {
                        result = "Error"
                    }
                }
                // Loại bỏ số 0 sau dấu chấm (nếu có)
                result = removeZeroAfterDot(result)
                tvInput?.text = result
            } catch (e: ArithmeticException) {
                tvInput?.text = "Error"
            }
            lastNumeric = true
        }
    }

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput?.text.toString())) {
            tvInput?.append((view as TextView).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        // Kiểm tra xem kết quả có chứa dấu chấm không
        if (value.contains(".")) {
            // Loại bỏ các ký tự '0' từ cuối chuỗi cho đến khi gặp ký tự khác
            value = value.replace("0*$".toRegex(), "")
            // Nếu kết quả sau khi loại bỏ toàn bộ số '0' vẫn kết thúc bằng dấu chấm, thì loại bỏ dấu chấm luôn
            if (value.endsWith(".")) {
                value = value.substring(0, value.length - 1)
            }
        }
        return value
    }
}