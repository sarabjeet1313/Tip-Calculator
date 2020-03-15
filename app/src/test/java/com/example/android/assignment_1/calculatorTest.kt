package com.example.android.assignment_1

import androidx.lifecycle.ViewModelProviders
import org.junit.Test

import org.junit.Assert.*
import com.example.android.assignment_1.ViewModel.tipCalViewModel
import com.example.android.assignment_1.Model.tipCalModel
import com.example.android.assignment_1.View.MainActivity
import org.robolectric.Robolectric


/**
 * Calculator test
 */
class calculatorTest {

    var  mActivity : MainActivity = Robolectric.setupActivity(MainActivity::class.java)
    var viewModel  = ViewModelProviders.of(mActivity).get(tipCalViewModel::class.java)
    var model : tipCalModel? = null

    @Test
    fun CalculateTest() {
        model = viewModel.calculate("20.00","10", "2" )
        if(model != null) {
            assertEquals(model!!.totalTip, "22.00")
        }

    }
}
