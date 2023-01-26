package com.example.githubapp.util.extensions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.util.helpers.SafeClickListener
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(url: String, @DrawableRes placeholder: Int = R.drawable.baseline_image_24) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.showErrorSnackbar(errorMessage: String) {
    Snackbar.make(this, errorMessage, Snackbar.LENGTH_LONG).show()
}
