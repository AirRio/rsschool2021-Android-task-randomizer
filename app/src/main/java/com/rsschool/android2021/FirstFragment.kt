package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment


interface OpenSecondFragment {
    fun openSecond(min: Int, max: Int)
}

class FirstFragment : Fragment() {

    private var listen: OpenSecondFragment? = null
    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OpenSecondFragment) listen = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val minView = view.findViewById<EditText>(R.id.min_value)
        val maxView = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            val minValue = minView.text.toString().toIntOrNull()
            val maxValue = maxView.text.toString().toIntOrNull()

            when {
                minValue == null -> minView.error = "Min shouldn't be empty or invalid"
                maxValue == null -> maxView.error = "Max shouldn't be empty or invalid"
                minValue >= maxValue -> maxView.error = "Max value must be greater then Min"
                else -> listen?.openSecond(minValue, maxValue)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}
