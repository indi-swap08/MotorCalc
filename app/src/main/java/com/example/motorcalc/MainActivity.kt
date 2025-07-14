package com.example.motorcalc

import android.content.Intent
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

        binding.insuranceLayout.visibility = View.GONE
        viewModel.downPayment.value = "15"
        viewModel.interest.value = "7.5"
        viewModel.processingFee.value = "1.5"
        viewModel.emiMonths.value = "48"
        viewModel.insurance.value = "2.5"

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
            binding.insuranceLayout.visibility = if (isChecked == true) View.VISIBLE else View.GONE
        }
    }
}
