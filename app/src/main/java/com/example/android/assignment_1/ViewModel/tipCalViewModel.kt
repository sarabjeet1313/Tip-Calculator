package com.example.android.assignment_1.ViewModel

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.assignment_1.Model.tipCalModel

/** View Model class of the application to provide
 *  the logic for the application and also to handle
 *  the live data.
 */
class tipCalViewModel : ViewModel() {

    //********* initializing local variables ************

    private var tipValue = "15"
    private var splitValue = "1"
    private var result : tipCalModel? = null

    //***************************************************


    // capturing the change in tip slider value using Live data.
    public val tValue = MutableLiveData<String>()
        .apply {
            value = tipValue.toString()

        }

    // updating the current tip value so as the observer in view class can see it.
    public var TipValue: MutableLiveData<String> = tValue



    // capturing the change in split slider value using Live data.
    public val sValue = MutableLiveData<String>()
        .apply {
            value = splitValue.toString()

        }

    // updating the current split value so as the oberver in view class can see it.
    public var SplitValue: MutableLiveData<String> = sValue




    /** The logic for the application.
     *
     *  It takes the values from the view class, apply the logic
     *  for calculating the tip and split amount and returns back the result.
     *
     *  @arguments : editText object, tipValue and the splitValue.
     *  @return : tipCalModel (model class) object with the calculated values.
     */
    public fun calculate(amount: String, tipValue : String, splitValue: String) : tipCalModel? {

        try {
                var amountValue: Double = amount.toDouble()
                var tipPercent: Int = tipValue.toInt()
                var split: Int = splitValue.toInt()

                // tip calculation.
                var totalTipValue: String = "%.2f".format(amountValue * (tipPercent / 100.0))

                // total bill.
                var total: String = "%.2f".format(amountValue + totalTipValue.toDouble())

                // bill per head.
                var splitAmount: String = "%.2f".format(total.toDouble() / split)

                // instantiate a model class object with final values and return the same.
                result = tipCalModel(totalTipValue, total, splitAmount)
            } catch (e : Exception) {
            return null
        }
        return result
    }
}