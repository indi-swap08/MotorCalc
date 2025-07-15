package com.example.motorcalc

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.motorcalc.databinding.ActivityMainBinding
import com.example.motorcalc.viewmodel.CalculateViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalculateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = CalculateViewModel()
        binding.calcVm = viewModel
        binding.lifecycleOwner = this

        setDefaultValues()

        binding.clear.setOnClickListener {
            setDefaultValues()
        }

        binding.submit.setOnClickListener {
            viewModel.calculate()
            binding.resultCard.visibility = View.VISIBLE
        }

        viewModel.motorInsurance.observe(this) { isChecked ->
            val animation = if (isChecked == true) {
                AnimationUtils.loadAnimation(this, R.anim.fade_in)
            } else {
                AnimationUtils.loadAnimation(this, R.anim.fade_out)
            }
            binding.insuranceLayout.startAnimation(animation)
            binding.insuranceLayout.visibility = if (isChecked == true) View.VISIBLE else View.INVISIBLE
        }
    }
    private fun setDefaultValues() {
        with(viewModel) {
            motorInsurance.value = false
            amount.value = ""
            discount.value = ""
            downPayment.value = "15"
            interest.value = "7.5"
            processingFee.value = "1.5"
            emiMonths.value = "48"
            insurance.value = "2.5"
        }
        binding.insuranceLayout.visibility = View.INVISIBLE
        binding.resultCard.visibility = View.GONE
    }
}

