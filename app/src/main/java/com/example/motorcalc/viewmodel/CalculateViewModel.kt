package com.example.motorcalc.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.System.console

class CalculateViewModel : ViewModel() {

    val amount = MutableLiveData<String>()
    val interest = MutableLiveData<String>()
    val discount = MutableLiveData<String>()
    val downPayment = MutableLiveData<String>()
    val processingFee = MutableLiveData<String>()
    val emiMonths = MutableLiveData<String>()
    val motorInsuranceChecked = MutableLiveData<Boolean>()
    val result = MutableLiveData<String>()

    fun calculate() {

        Log.i("Calculate","Raw inputs: amount='${amount.value}', " +
                "insurance = '${motorInsuranceChecked.value}', " +
                "discount='${discount.value}', downPayment='${downPayment.value}'")
        val amt = amount.value?.toDoubleOrNull() ?: 0.0
        val intRate = interest.value?.toDoubleOrNull() ?: 0.0
        val disc = discount.value?.toDoubleOrNull() ?: 0.0
        val downPayPercent = downPayment.value?.toDoubleOrNull() ?: 0.0
        val procFee = processingFee.value?.toDoubleOrNull() ?: 0.0
        val emiMonthsVal = emiMonths.value?.toIntOrNull() ?: 0
        val isInsurance = motorInsuranceChecked.value == true

        val afterDiscount = if (disc > amt) amt else amt - disc
        val insurance = if (isInsurance) (afterDiscount * 2.5 / 100) else 0.0
        val total = afterDiscount + insurance
        val downPay = (total * downPayPercent / 100)
        val financeAmt = total - downPay
        val financePercent = if (downPay < 100) (100 - downPayPercent) else 0.0
        val processFee = (financeAmt * procFee / 100)
        val totalDownPay = downPay + processFee
        val emiYear = emiMonthsVal / 12
        val totalInterest = (financeAmt * intRate / 100) * emiYear
        val emiAmt = if (emiMonthsVal > 0) (financeAmt + totalInterest) / emiMonthsVal else 0.0

        val fmt = "%.2f" // format to 2 decimals

        result.value = """
            Vehicle Price: ${fmt.format(amt)}
            Discount: ${fmt.format(disc)}
            Net Vehicle After OFFER: ${fmt.format(afterDiscount)}
            Motor Insurance (2.5%): ${fmt.format(insurance)}
            TOTAL PRICE: ${fmt.format(total)}
            Down Payment (${fmt.format(downPayPercent)}%): ${fmt.format(downPay)}
            Finance Amount (${fmt.format(financePercent)}%): ${fmt.format(financeAmt)}
            Processing Fee (${fmt.format(procFee)}%): ${fmt.format(processFee)}
            Total Down Payment: ${fmt.format(totalDownPay)}
            Interest (${fmt.format(intRate)}%): ${fmt.format(totalInterest)}
            EMI: $emiYear years ($emiMonthsVal months)
            Amount per month: ${fmt.format(emiAmt)}
        """.trimIndent() }
}
