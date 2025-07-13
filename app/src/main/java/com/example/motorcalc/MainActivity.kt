package com.example.motorcalc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motorcalc.databinding.ActivityMainBinding
import com.example.motorcalc.viewmodel.CalculateViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalculateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = CalculateViewModel()
        binding.calcVm = viewModel
        binding.lifecycleOwner = this

        viewModel.downPayment.value = "15"
        viewModel.interest.value = "7.5"
        viewModel.processingFee.value = "1.5"
        viewModel.emiMonths.value = "48"

        binding.submit.setOnClickListener {
            viewModel.calculate()
        }

        viewModel.result.observe(this) { result ->
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("result", result)
            startActivity(intent)
//            binding.textCalculate.text = result
        }
    }
}
