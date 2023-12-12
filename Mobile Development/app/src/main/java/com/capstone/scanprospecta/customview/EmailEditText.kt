package com.capstone.scanprospecta.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.capstone.scanprospecta.R

class EmailEditText : AppCompatEditText {
    private lateinit var editTextBackground: Drawable
    private lateinit var editTextErrorBackground: Drawable
    private var isError = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Enter your email"
        background = if (isError) editTextErrorBackground else editTextBackground
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            if (!isEmailValid(text.toString())) {
                setError("Please input a valid email")
                isError = true
            } else {
                error = null
                isError = false
            }
        })
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun init() {
        editTextBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text) as Drawable
        editTextErrorBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error) as Drawable
    }
}