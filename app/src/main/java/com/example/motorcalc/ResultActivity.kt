package com.example.motorcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motorcalc.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get result string from intent
        val result = intent.getStringExtra("result") ?: "No result"

        // Show result
        binding.textResult.text = result
    }
}
