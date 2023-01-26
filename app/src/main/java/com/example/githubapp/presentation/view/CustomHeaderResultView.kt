package com.example.githubapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.githubapp.R
import com.example.githubapp.databinding.ViewCustomHeaderResultBinding
import com.google.android.material.card.MaterialCardView

class CustomHeaderResultView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attributeSet, defStyleAttr) {

    private var binding: ViewCustomHeaderResultBinding

    init {
        binding = ViewCustomHeaderResultBinding.inflate(LayoutInflater.from(context))
        addView(binding.root).also {
            attributeSet?.let {
                context.obtainStyledAttributes(attributeSet, R.styleable.CustomHeaderResultView, 0, 0).apply {
                    try {
                        binding.titleTV.text = getString(R.styleable.CustomHeaderResultView_header)
                        binding.resultTV.text = getString(R.styleable.CustomHeaderResultView_result)
                    } finally {
                        recycle()
                    }
                }
            }
        }
    }

    fun setResult(result: String) {
        binding.resultTV.text = result
    }
}
