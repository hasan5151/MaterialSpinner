package com.huzzy.materialspinner

import android.content.Context
import android.graphics.drawable.*
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat


class MaterialSpinner(context: Context, attr: AttributeSet) : LinearLayout(context, attr) {

    init {
        initializeViews(context, attr)
    }

    private lateinit var errorTextView: TextView
    private lateinit var spinner: Spinner
    private lateinit var mLabel: TextView
    private lateinit var spinnerLayout: LinearLayout

    private fun initializeViews(context: Context, attr: AttributeSet) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.material_custom_spinner, this, true)

        orientation = VERTICAL
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val typedArray = context.obtainStyledAttributes(attr, R.styleable.MaterialSpinner, 0, 0)
        val labelText = typedArray.getString(R.styleable.MaterialSpinner_labelText)
        val labelBackgroundColor = typedArray.getColor(R.styleable.MaterialSpinner_labelBgColor,ContextCompat.getColor(context,R.color.bg))
        val widgetColor = typedArray.getColor(R.styleable.MaterialSpinner_widgetColor,ContextCompat.getColor(context,R.color.white))
        val widgetTick = typedArray.getInt(R.styleable.MaterialSpinner_widgetTickness,3)
        val labelSize = typedArray.getDimension(R.styleable.MaterialSpinner_labelSize,12F)
        val labelColor = typedArray.getColor(R.styleable.MaterialSpinner_labelColor,ContextCompat.getColor(context,R.color.hint_text_color))

        typedArray.recycle()

        spinnerLayout =  findViewById(R.id.spinner_layout)
        errorTextView = findViewById(R.id.error)
        spinner = findViewById(R.id.spinner)
        mLabel = findViewById(R.id.label)


        if (labelText==null) {
            mLabel.visibility = View.GONE
        } else {
            mLabel.text = labelText
            mLabel.visibility = View.VISIBLE
         }


        val layerDrawable = spinnerLayout.background as LayerDrawable
        val gradientDrawable = layerDrawable
            .findDrawableByLayerId(R.id.drawable) as GradientDrawable
        gradientDrawable.setStroke(widgetTick,widgetColor)

        mLabel.setBackgroundColor(labelBackgroundColor)
        mLabel.textSize = labelSize
        mLabel.setTextColor(labelColor)
    }

    fun setWidgetColor(color : Int){
        var layerDrawable = spinnerLayout.background as LayerDrawable
        val gradientDrawable = layerDrawable
            .findDrawableByLayerId(R.id.drawable) as GradientDrawable
        gradientDrawable.setStroke(3,color)
    }

    fun getSpinner(): Spinner {
        return spinner
    }

    fun setItemSelectedListener(onItemSelectedListener: AdapterView.OnItemSelectedListener) {
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    fun setAdapter(adapter: SpinnerAdapter) {
        spinner.adapter = adapter
    }

    fun setLabel(labelText: String) {
        if (labelText == null || labelText.isEmpty()) {
            mLabel.visibility = View.GONE
        } else {
            mLabel.text = labelText
            mLabel.visibility = View.VISIBLE
        }
    }

    fun setError(error: String) {
        if (error!=null && error.trim().isNotEmpty()) {
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = error
//            spinnerLayout.setBackgroundResource(R.drawable.payment_edit_error_bg)
        //    spinner.setBackgroundColor(widgetColor)
        } else {
            errorTextView.visibility = View.INVISIBLE
            spinnerLayout.setBackgroundResource(R.drawable.payment_edit_bg)
        }
    }

    fun setErrorEnabled(isErrorEnabled : Boolean) {
        if (isErrorEnabled) {
            spinnerLayout.setBackgroundResource(R.drawable.payment_edit_error_bg)
            errorTextView.visibility = View.VISIBLE

        } else {
            errorTextView.visibility = View.INVISIBLE
         //   spinnerLayout.setBackgroundResource(R.drawable.payment_edit_bg)
        }
    }
}