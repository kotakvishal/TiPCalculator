package com.example.tipcalculator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
   private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        displayTip(0.0)
        activityMainBinding.calculateButton.setOnClickListener { calculateTip() }
        activityMainBinding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

    }

    private fun calculateTip() {
        val stringInTextField= activityMainBinding.costOfServiceEditText.text.toString()
        val cost=stringInTextField.toDoubleOrNull()
        val tipPercentage=when(activityMainBinding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent-> 0.20
            R.id.option_eighteen_percent->0.18
            R.id.option_fifteen_percent->0.15
            else ->0.15
        }
        if(cost==null || cost==0.0){
            displayTip(0.0)
            return
        }
        var tip= tipPercentage* cost
        val roundUp= activityMainBinding.roundUpSwitch.isChecked
        if(roundUp){
            kotlin.math.ceil(tip).also { tip = it }
        }
        displayTip(tip)
    }
    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        activityMainBinding.tipResults.text = getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
    }
