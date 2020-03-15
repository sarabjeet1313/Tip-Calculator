package com.example.android.assignment_1.View

import com.example.android.assignment_1.ViewModel.tipCalViewModel
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.assignment_1.Model.tipCalModel
import com.example.android.assignment_1.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

/**
 *  View class of the application to instantiate and inflate
 *  the various views with the processed values fetched from ViewModel.
 */
class MainActivity : AppCompatActivity() {


    lateinit var viewModel: tipCalViewModel // view model object declaration.
    lateinit var amount: EditText  // editText type object declaration to get/set amount entered by user.
    lateinit var sliderTip: SeekBar  // seekbar object declaration for tip.
    lateinit var sliderSplit: SeekBar // seekbar object declaration for split.
    lateinit var tipValue: TextView  // textView object declaration for tip slider value.
    lateinit var totalTip: TextView  // textView object declaration for total tip.
    lateinit var splitValue: TextView  // textView object declaration for total slider tip.
    lateinit var perHeadAmountValue: TextView  // textView object declaration for per head bill.
    lateinit var totalAmount: TextView  // textView object declaration for total bill.

    /**
     * Overriding the onCreate() method.
     *
     * @arguments : Bundle => to store the state of the activity so as to restore them
     *              using the data store in bundle.
     *
     * @Operations performed :
     *  1. Instantiating the view objects.
     *  2. Enabling and attaching the click listeners for every key.
     *  3. Providing seekbar or slider functionalities and override the necessary abstract methods.
     *  4. Attaching an observer to the live data to capture any change in data.
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState) // calling the parent onCreate with the appropriate argument.

        // fetching the viewModel object
        viewModel = ViewModelProviders.of(this).get(tipCalViewModel::class.java)

        // binding the view or xml or layout file
        setContentView(R.layout.activity_main)

        // ********************** instantiating the objects **********************

        sliderTip = findViewById(R.id.seekBar) as SeekBar
        sliderTip.setProgress(15)
        sliderSplit = findViewById(R.id.seekBar2) as SeekBar
        sliderSplit.setProgress(1)
        tipValue = findViewById(R.id.tipValue) as TextView
        amount = findViewById(R.id.amountText) as EditText
        totalTip = findViewById(R.id.totalTip) as TextView
        splitValue = findViewById(R.id.splitValue) as TextView
        perHeadAmountValue = findViewById(R.id.perHeadAmountValue) as TextView
        totalAmount = findViewById(R.id.totalAmount) as TextView

        // *************************************************************************


        // call to enable click listeners for every key.
        listenClicks()

        // recording the seekbar change and performing necessary actions as the tip slider progresses.
        sliderTip.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val tip: String = progress.toString()
                viewModel.tValue.value = tip

                // error handling for blank or not accepted amount
                if (amount.text.trim().isEmpty() || amount.text.trim().isBlank() || amount.text.toString() == "0" || amount.text.toString() == ".") {
                    amount.requestFocus()
                    amount.setError("Please fill the amount first !!!")

                    reset()
                }
                // else call to the app logic or calculate method in viewModel to process the data and fetch the result.
                else {
                    val result: tipCalModel? = viewModel.calculate(
                        amount.text.toString(),
                        tipValue.text.toString(),
                        splitValue.text.toString()
                    )
                    // calling the method to inflate the various view objects with the fetched result.
                    inflateResult(result)
                }
            }

            // abstract members overridden.
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        // Observer on the live data "TipValue" to update the tipValue as soon as the tip slider changes.
        viewModel.TipValue.observe(this, Observer {
            tipValue.text = it
        })


        // recording the seekbar change and performing necessary actions as the split slider progresses.
        sliderSplit.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val split: String = progress.toString()
                viewModel.sValue.value = split

                // error handling for blank or not accepted amount
                if (amount.text.trim().isEmpty() || amount.text.trim().isBlank() || amount.text.toString() == "0" || amount.text.toString() == ".") {
                    amount.requestFocus()
                    amount.setError("Please fill the amount first !!!")

                    reset()
                }
                // else call to the app logic or calculate method in viewModel to process the data and fetch the result.
                else {
                    val result: tipCalModel? = viewModel.calculate(
                        amount.text.toString(),
                        tipValue.text.toString(),
                        splitValue.text.toString()
                    )
                    // calling the method to inflate the various view objects with the fetched result.
                    inflateResult(result)
                }
            }

            // abstract members overridden.
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        // Observer on the live data "SplitValue" to update the splitValue as soon as the split slider changes.
        viewModel.SplitValue.observe(this, Observer {
            splitValue.text = it
        })

    }

    /**
     * This method enables and attaches the click listeners for every necessary button.
     * Also it stops the native keyboard to come up when user touches the editText to enter amount.
     *
     * For keys 0-9 and "." , only the corresponding character will be appended to the amount.
     *
     * For Enter key, logic in viewModel will be called and the resulting values will be
     *                set to the respective views.
     *
     * For Backspace key, only the 0 - length-1 characters will be captured.
     *
     * For Reset key, the reset function will be called.
     *
     */
    fun listenClicks() {
        try {

            key1.setOnClickListener {
                amount.append("1")
            }

            key2.setOnClickListener {
                amount.append("2")
            }

            key3.setOnClickListener {
                amount.append("3")
            }

            key4.setOnClickListener {
                amount.append("4")
            }

            key5.setOnClickListener {
                amount.append("5")
            }

            key6.setOnClickListener {
                amount.append("6")
            }

            key7.setOnClickListener {
                amount.append("7")
            }

            key8.setOnClickListener {
                amount.append("8")
            }

            key9.setOnClickListener {
                amount.append("9")
            }

            key0.setOnClickListener {
                amount.append("0")
            }

            decimal.setOnClickListener {
                if (!amount.text.contains(".")) {  // error handling of multiple decimals
                    amount.append(".")
                }
            }

            backspaceKey.setOnClickListener {
                var str: String = amount.text.toString() // fetch the current text in amount editText.
                var ln: Int = amount.text.length // get the length of the text.

                // if the text is not empty and backspace key is pressed, remove the nth character.
                if (!amount.text.isEmpty()) {
                    amount.setText(str.substring(0, ln - 1)) // take only 0 - length-1 characters, discard the last one.
                    amount.setSelection(amount.text.length)
                    
                    if (amount.text.trim().isEmpty() || amount.text.trim().isBlank() || amount.text.toString() == "0" || amount.text.toString() == ".") {
                        amount.requestFocus()
                        amount.setError("Please fill the amount first !!!")

                        reset()
                    }
                    // else call to the app logic or calculate method in viewModel to process the data and fetch the result.
                    else {
                        val result: tipCalModel? = viewModel.calculate(
                            amount.text.toString(),
                            tipValue.text.toString(),
                            splitValue.text.toString()
                        )
                        // calling the method to inflate the various view objects with the fetched result.
                        inflateResult(result)
                    }

                }
            }

            enterKey.setOnClickListener {

                // error handling for blank or not accepted amount
                if (amount.text.trim().isEmpty() || amount.text.trim().isBlank() || amount.text.toString() == "0" || amount.text.toString() == ".") {
                    amount.requestFocus()
                    amount.setError("Please fill the amount first !!!")
                    reset()
                }
                // else call to the app logic or calculate method in viewModel to process the data and fetch the result.
                else {
                    val result: tipCalModel? = viewModel.calculate(
                        amount.text.toString(),
                        tipValue.text.toString(),
                        splitValue.text.toString()
                    )
                    // calling the method to inflate the various view objects with the fetched result.
                    inflateResult(result)
                }
            }


            reset.setOnClickListener {
                reset()  // this call will reset the views to the appropriate values.

            }

            // hide the native keyboard when the focus is on amount editText.
            amount.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent): Boolean {
                    hideKeyBoard()
                    return true
                }
            })

        } catch (e : Exception) {
            reset()
        }
    }


    /**
     * Method to cease the native keyboard to come up when focus comes on amount editText.
     * This will let the user enter the amount using the custom keyboard.
     */
    fun hideKeyBoard() {
        try {
            val view = this.currentFocus
            if (view != null) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view!!.windowToken, 0)

            }
        }catch(e: Exception) {}
    }


    // Method with set the fetched result to the respective views.
    fun inflateResult(result: tipCalModel?) {

        if(result != null) {
            totalTip.text = "CAD " + result.totalTip
            totalAmount.text = "CAD " + result.total
            perHeadAmountValue.text = "CAD " + result.perHeadSplit
//            perHeadTipValue.text = "C$" + result.perHeadTip
        }
    }

    // this will reset views to the default values.
    fun reset() {
        sliderTip.setProgress(15)
        sliderSplit.setProgress(1)
        amount.setText("")
        perHeadAmountValue.setText("0")
        totalTip.setText("0")
        totalAmount.setText("0")
    }
}

