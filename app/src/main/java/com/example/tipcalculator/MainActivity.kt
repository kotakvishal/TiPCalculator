package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
   private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.calculateButton.setOnClickListener { calculateTip() }
       displayTip(0.0)
    }

    private fun calculateTip() {
        val stringInTextField=activityMainBinding.costOfService.text.toString()
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
        val roundUp= this.activityMainBinding.roundUpSwitch.isChecked
        if(roundUp){
            kotlin.math.ceil(tip).also { tip = it }
        }
        displayTip(tip)
    }
    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        activityMainBinding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}