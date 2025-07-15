package com.example.motorcalc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Locale

class CalculateViewModel : ViewModel() {

    val amount = MutableLiveData<String>()
    val interest = MutableLiveData<String>()
    val discount = MutableLiveData<String>()
    val downPayment = MutableLiveData<String>()
    val processingFee = MutableLiveData<String>()
    val emiMonths = MutableLiveData<String>()
    val motorInsurance = MutableLiveData<Boolean>()
    val insurance = MutableLiveData<String>()

    val showInsuranceRow = MutableLiveData<Boolean>()

    // Formatted result fields
    val formattedAmt = MutableLiveData<String>()
    val formattedDisc = MutableLiveData<String>()
    val formattedAfterDiscount = MutableLiveData<String>()
    val formattedInsurance = MutableLiveData<String>()
    val formattedTotal = MutableLiveData<String>()
    val downPayPercent = MutableLiveData<String>()
    val formattedDownPay = MutableLiveData<String>()
    val financePercent = MutableLiveData<String>()
    val formattedFinanceAmt = MutableLiveData<String>()
    val procFee = MutableLiveData<String>()
    val formattedProcessFee = MutableLiveData<String>()
    val formattedTotalDownPay = MutableLiveData<String>()
    val intRate = MutableLiveData<String>()
    val formattedTotalInterest = MutableLiveData<String>()
    val emiYear = MutableLiveData<String>()
    val emiMonthsVal = MutableLiveData<String>()
    val formattedEmiAmt = MutableLiveData<String>()

    // Custom QAR formatter (English format)
    private val qarFormatter: NumberFormat by lazy {
        NumberFormat.getNumberInstance(Locale.ENGLISH).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
    }

    fun formatAsQar(amount: Double): String {
        return "QAR ${qarFormatter.format(amount)}"
    }

    fun calculate() {
        val amt = amount.value?.toDoubleOrNull() ?: 0.0
        val intRateVal = interest.value?.toDoubleOrNull() ?: 0.0
        val disc = discount.value?.toDoubleOrNull() ?: 0.0
        val downPayPercentVal = downPayment.value?.toDoubleOrNull() ?: 0.0
        val procFeeVal = processingFee.value?.toDoubleOrNull() ?: 0.0
        val emiMonthsValInt = emiMonths.value?.toIntOrNull() ?: 0
        val insurancePercent = insurance.value?.toDoubleOrNull() ?: 0.0
        val isInsurance = motorInsurance.value == true

        // Calculations
        val afterDiscount = if (disc > amt) amt else amt - disc
        val insuranceAmount = if (isInsurance) (afterDiscount * insurancePercent / 100) else 0.0
        val total = afterDiscount + insuranceAmount
        val downPay = (total * downPayPercentVal / 100)
        val financeAmt = total - downPay
        val financePercentVal = if (downPayPercentVal < 100) (100 - downPayPercentVal) else 0.0
        val processFee = (financeAmt * procFeeVal / 100)
        val totalDownPay = downPay + processFee
        val emiYearVal = emiMonthsValInt / 12
        val totalInterest = (financeAmt * intRateVal / 100) * emiYearVal
        val emiAmt = if (emiMonthsValInt > 0) (financeAmt + totalInterest) / emiMonthsValInt else 0.0


        // Update LiveData with QAR-formatted values
        formattedAmt.value = formatAsQar(amt)
        formattedDisc.value = formatAsQar(disc)
        formattedAfterDiscount.value = formatAsQar(afterDiscount)
        formattedInsurance.value = formatAsQar(insuranceAmount)
        formattedTotal.value = formatAsQar(total)
        formattedDownPay.value = formatAsQar(downPay)
        formattedFinanceAmt.value = formatAsQar(financeAmt)
        formattedProcessFee.value = formatAsQar(processFee)
        formattedTotalDownPay.value = formatAsQar(totalDownPay)
        formattedTotalInterest.value = formatAsQar(totalInterest)
        formattedEmiAmt.value = formatAsQar(emiAmt)

        showInsuranceRow.value = isInsurance

        // Percentages remain the same
        downPayPercent.value = "%.2f".format(downPayPercentVal)
        financePercent.value = "%.2f".format(financePercentVal)
        procFee.value = "%.2f".format(procFeeVal)
        intRate.value = "%.2f".format(intRateVal)

        // EMI period
        emiYear.value = emiYearVal.toString()
        emiMonthsVal.value = emiMonthsValInt.toString()
    }
}
