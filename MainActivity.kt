package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.EditText
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var resultText: EditText
    private var currentInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultText = findViewById(R.id.result)
        val button1: Button = findViewById(R.id.number1)
        val button2: Button = findViewById(R.id.number2)
        val button3: Button = findViewById(R.id.number3)
        val button4: Button = findViewById(R.id.number4)
        val button5: Button = findViewById(R.id.number5)
        val button6: Button = findViewById(R.id.number6)
        val button7: Button = findViewById(R.id.number7)
        val button8: Button = findViewById(R.id.number8)
        val button9: Button = findViewById(R.id.number9)
        val button0: Button = findViewById(R.id.number0)
        val buttonMultiply: Button = findViewById(R.id.btnMultiply)
        val buttonDivide: Button = findViewById(R.id.btnDivide)
        val buttonAdd: Button = findViewById(R.id.btnAdd)
        val buttonSubtract: Button = findViewById(R.id.btnSubtract)
        val buttonErased: Button = findViewById(R.id.erased)
        val equal: Button = findViewById(R.id.equal)

        button1.setOnClickListener{
            appendText("1")
        }
        button2.setOnClickListener{
            appendText("2")
        }
        button3.setOnClickListener{
            appendText("3")
        }
        button4.setOnClickListener{
            appendText("4")
        }
        button5.setOnClickListener{
            appendText("5")
        }
        button6.setOnClickListener{
            appendText("6")
        }
        button7.setOnClickListener{
            appendText("7")
        }
        button8.setOnClickListener{
            appendText("8")
        }
        button9.setOnClickListener{
            appendText("9")
        }
        button0.setOnClickListener{
            appendText("0")
        }
        buttonAdd.setOnClickListener {
            appendText("+")
        }
        buttonSubtract.setOnClickListener {
            appendText("-")
        }
        buttonMultiply.setOnClickListener {
            appendText("*")
        }
        buttonDivide.setOnClickListener {
            appendText("/")
        }
        buttonErased.setOnClickListener {
            popText()
        }
        equal.setOnClickListener {
            calculateResult()
        }
    }

    private fun appendText(value: String){
        if (currentInput.length >= 35) {
            Toast.makeText(this, "Limite de caracteres atingido", Toast.LENGTH_SHORT).show()
            return
        }

        if (value.isEmpty()) return
        val sanitizedValue = if (value == ",") "." else value
        currentInput += sanitizedValue

        val formatInput = currentInput.replace("/", "÷").replace(".", ",")
        resultText.setText(formatInput)
    }

    private fun popText(){
        if (currentInput.isNotEmpty()){
            currentInput = currentInput.dropLast(1)

            if (currentInput.isNotEmpty() &&
                (currentInput.last() == '+' || currentInput.last() == '-' ||
                 currentInput.last() == '*' || currentInput.last() == '÷')){
                currentInput = currentInput.dropLast(1)
            }
        }
        resultText.setText(currentInput)
    }

    @SuppressLint("SetTextI18n")
    private fun calculateResult() {
        if (currentInput.isEmpty() || currentInput.length > 35) {
            Toast.makeText(this, "Expressão inválida ou muito longa", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val sanitizedInput = currentInput.replace(",", ".")

            val result = evaluateExpression(sanitizedInput)

            val maxResult = 999999999.99
            val finalResult = if (result > maxResult) maxResult else result

            val formattedResult = String.format("%.2f", finalResult).replace(".", ",")

            resultText.setText(formattedResult)
            currentInput = formattedResult.replace(",", ".") // Internamente usa ponto
        } catch (e: Exception) {
            Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



    private fun evaluateExpression(expression: String): Double {
        val evaluator = net.objecthunter.exp4j.
        ExpressionBuilder(expression).build()
        return evaluator.evaluate()
    }

}