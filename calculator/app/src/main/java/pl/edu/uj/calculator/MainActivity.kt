package pl.edu.uj.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    enum class TypeOfOperation {
        ADD, SUB, MULT, DIV, MODULO, NONE
    }

    var typeOfOperation = TypeOfOperation.NONE
    var a: Double = 0.0
    var b: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtViewOperation: TextView = findViewById(R.id.txtViewTypeOfOperation)

        val addButton = findViewById<Button>(R.id.btAdd)

        addButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.ADD
            txtViewOperation.text = "+"
        }

        val subButton = findViewById<Button>(R.id.btSub)

        subButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.SUB
            txtViewOperation.text = "-"
        }

        val multButton = findViewById<Button>(R.id.btMult)

        multButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.MULT
            txtViewOperation.text = "x"
        }

        val divButton = findViewById<Button>(R.id.btDiv)

        divButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.DIV
            txtViewOperation.text = "/"
        }

        val modButton = findViewById<Button>(R.id.btMod)

        modButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.MODULO
            txtViewOperation.text = "%"
        }

        val clearButton = findViewById<Button>(R.id.btClear)

        clearButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.NONE
            txtViewOperation.text = ""
            a=0.0
            b=0.0

            val inputA: EditText = findViewById(R.id.inputA)
            inputA.text.clear()

            val inputB: EditText = findViewById(R.id.inputB)
            inputB.text.clear()

            val txtViewResult: TextView = findViewById(R.id.textViewResult)
            val stringTutorial: String = getString(R.string.resultTxtViewTutorial)
            txtViewResult.text = stringTutorial

        }

        val calculateButton = findViewById<Button>(R.id.btCalculate)

        calculateButton.setOnClickListener {
            calculate()
        }
    }



    fun calculate() {

        val inputA: EditText = findViewById(R.id.inputA)
        a = inputA.text.toString().toDouble()

        val inputB: EditText = findViewById(R.id.inputB)
        b= inputB.text.toString().toDouble()

        val textViewResult: TextView = findViewById(R.id.textViewResult)
        if(typeOfOperation == TypeOfOperation.NONE) {
            textViewResult.setText("Choose type of operation! (+, - etc.")
            return
        }
        if(b == 0.0 && typeOfOperation == TypeOfOperation.DIV) {
            textViewResult.setText("You can't divide by zero!!")
            return
        }

        var result: String = ""
        result += a
        var resultNumber = 0.0
        when(typeOfOperation) {
            TypeOfOperation.ADD ->  {resultNumber = a+b; result += " + "}
            TypeOfOperation.SUB -> {resultNumber = a-b; result += " - "}
            TypeOfOperation.DIV -> {resultNumber = a/b; result += " / "}
            TypeOfOperation.MULT -> {resultNumber = a*b; result += " x "}
            TypeOfOperation.MODULO -> {resultNumber = a%b; result += " % "}
            else -> System.err.println("unspecified operation")
        }
        result += b
        result += " = "
        result += resultNumber

        textViewResult.text = result
    }


}