package com.example.foody.ui.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.foody.databinding.FloatButtonLayoutBinding

class CustomisedFloatButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = FloatButtonLayoutBinding.inflate(LayoutInflater.from(context), this, true)

}