package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.app.DatePickerDialog
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentProvideInformationBinding
import com.project.elderlyhealthcare.databinding.FragmentSignUpBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.fragment.main.event.UpdateReExaminationFragmentArgs
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class ProvideInformationFragment :
    BaseFragment<NotLoginViewModel, FragmentProvideInformationBinding>(R.layout.fragment_provide_information) {

    private val navArgs: ProvideInformationFragmentArgs by navArgs()

    override fun variableId(): Int = BR.provideInfoViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentProvideInformationBinding {
        return FragmentProvideInformationBinding.bind(view)
    }


    override fun init() {
        super.init()
        binding.apply {
            provideInfoFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            layoutProvideInfo.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
            provideInfoEdElderDob.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })


            provideInfoBtVerifyPhoneNumber.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkCustomerInfo ()
                    findNavController().navigate(ProvideInformationFragmentDirections.actionProvideInformationFragmentToVerifyPhoneNumberFragment())
                }
            })
            val adapterItems =
                ArrayAdapter(requireContext(), R.layout.item_prefecture, Constant.listPrefecture)
            provideInfoEdPrefecture.setAdapter(adapterItems)
        }
    }

    private fun checkCustomerInfo() {

    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.apply {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.provideInfoEdElderDob.setText(
                        getString(
                            R.string.day_month_year,
                            selectedDay.toString(),
                            (selectedMonth + 1).toString(),
                            selectedYear.toString()
                        )
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }
}