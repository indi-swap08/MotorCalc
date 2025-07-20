package com.example.motorcalc.utils

import android.text.InputFilter
import android.text.Spanned

class InputFilterMax( private val max: Double) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val newText = dest.toString().substring(0, dstart) +
                source.toString() +
                dest.toString().substring(dend)
        val input = newText.toDouble()

        if (input <= max) {
            return null // Accept input
        }
        return "" // Reject input
    }
}