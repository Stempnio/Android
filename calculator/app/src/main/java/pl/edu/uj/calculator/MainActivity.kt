package pl.edu.uj.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val addButton = findViewById<Button>(R.id.btAdd)

        addButton.setOnClickListener {
            typeOfOperation = TypeOfOperation.ADD

            val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
            tv1.text = "+"
        }
    }

    enum class TypeOfOperation {
        ADD, SUB, MULT, DIV, MODULO, NONE
    }

    var typeOfOperation = TypeOfOperation.NONE
    var a = 0
    var b = 0

//    fun calculate(view: android.view.View) {
//        setContentView(R.layout.activity_main)
//        val textViewResult: TextView = findViewById(R.id.textViewResult)
//        if(typeOfOperation == TypeOfOperation.NONE) {
//            textViewResult.setText("Choose type of operation! (+, - etc.")
//            return
//        }
//        if(b == 0 && typeOfOperation == TypeOfOperation.DIV) {
//            textViewResult.setText("You can't divide by zero!!")
//            return
//        }
//
//        var result: String = ""
//        result += a
//        var resultNumber = 0
//        when(typeOfOperation) {
//            TypeOfOperation.ADD ->  {resultNumber = a+b; result += " + "}
//            TypeOfOperation.SUB -> {resultNumber = a-b; result += " - "}
//            TypeOfOperation.DIV -> {resultNumber = a/b; result += " / "}
//            TypeOfOperation.MULT -> {resultNumber = a*b; result += " x "}
//            TypeOfOperation.MODULO -> {resultNumber = a%b; result += " % "}
//            else -> System.err.println("unspecified operation")
//        }
//        result += b
//        result += " = "
//        result += resultNumber
//
//        textViewResult.text = result
//    }

    fun adddd() {
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        textViewResult.setText("he")
    }
    fun add(view: android.view.View) {
        typeOfOperation = TypeOfOperation.ADD

        setContentView(R.layout.activity_main)
        val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
        tv1.text = "+"
    }
    fun sub(view: android.view.View) {
        typeOfOperation = TypeOfOperation.SUB

        setContentView(R.layout.activity_main)
        val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
        tv1.text = "-"
    }
    fun div(view: android.view.View) {
        typeOfOperation = TypeOfOperation.DIV

        setContentView(R.layout.activity_main)
        val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
        tv1.text = "/"
    }
    fun mult(view: android.view.View) {
        typeOfOperation = TypeOfOperation.MULT

        setContentView(R.layout.activity_main)
        val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
        tv1.text = "x"
    }
    fun mod(view: android.view.View) {
        typeOfOperation = TypeOfOperation.MODULO

        setContentView(R.layout.activity_main)
        val tv1: TextView = findViewById(R.id.txtViewTypeOfOperation)
        tv1.text = "%"
    }

    fun clear(view: android.view.View) {
//        setContentView(R.layout.activity_main)
//        val tvOp: TextView = findViewById(R.id.textViewOperation)
//        tvOp.text = ""
//
//        val editTextA: EditText = findViewById(R.id.inputA)
//        editTextA.setText("")
    }

    fun submitA(view: android.view.View) {
        setContentView(R.layout.activity_main)

        val EditTextinputA: EditText = findViewById(R.id.inputA)
        val string: String = EditTextinputA.text.toString()
        //a = Integer.parseInt(string)
    }

    fun submitB(view: android.view.View) {
        setContentView(R.layout.activity_main)
    }

}