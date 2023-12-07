package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.app.DatePickerDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentProvideInformationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import com.project.elderlyhealthcare.utils.Utils.textIsNull
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
            with(provideInfoEdElderDob) {
                showSoftInputOnFocus = false
                isFocusable = false
                setOnClickListener(object : SingleClickListener() {
                    override fun onSingleClick(v: View) {
                        selectDate()
                    }
                })
            }



            provideInfoBtVerifyPhoneNumber.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkCustomerInfo()
                }
            })
            val adapterItems =
                ArrayAdapter(requireContext(), R.layout.item_prefecture, Constant.listPrefecture)
            provideInfoEdPrefecture.setAdapter(adapterItems)
        }
    }

    private fun checkCustomerInfo() {
        binding.apply {
            if (textIsNull(provideInfoEdElderName) ||
                textIsNull(provideInfoEdElderDob) ||
                textIsNull(provideInfoEdElderWeight) ||
                textIsNull(provideInfoEdElderHeight) ||
                textIsNull(provideInfoEdAddress) ||
                provideInfoEdPrefecture.text.trim().toString().isEmpty()
            ) {
                setBackgroundLayoutEditText()
            } else {
                setBackgroundLayoutEditText()
                if (provideInfoRadioBtMale.isChecked || provideInfoRadioBtFemale.isChecked) {
                    with(navArgs.customerInfoModel) {
                        elderName = getTextFromEdittext(provideInfoEdElderName)
                        elderDob = getTextFromEdittext(provideInfoEdElderDob)
                        elderGender = provideInfoRadioGroup.getUserGender()
                        elderWeight = getTextFromEdittext(provideInfoEdElderWeight).toInt()
                        elderHeight = getTextFromEdittext(provideInfoEdElderHeight).toInt()
                        address = getTextFromEdittext(provideInfoEdAddress)
                        prefecture = provideInfoEdPrefecture.text.toString().trim()
                    }
                    findNavController().navigate(ProvideInformationFragmentDirections.actionProvideInformationFragmentToVerifyPhoneNumberFragment(navArgs.customerInfoModel))
                } else {
                    showDialog(requireContext(), "Vui lòng chọn giới tính")
                }
            }
        }
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
            datePickerDialog.show()
        }
    }

    private fun setBackgroundLayoutEditText() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            provideInfoLayoutEdElderName.error =
                if (textIsNull(provideInfoEdElderName)) errorMsg else null
            provideInfoLayoutEdBirthday.error =
                if (textIsNull(provideInfoEdElderDob)) errorMsg else null
            provideInfoLayoutEdWeight.error =
                if (textIsNull(provideInfoEdElderWeight)) errorMsg else null
            provideInfoLayoutEdHeight.error =
                if (textIsNull(provideInfoEdElderHeight)) errorMsg else null
            provideInfoLayoutEdAddress.error =
                if (textIsNull(provideInfoEdAddress)) errorMsg else null
            provideInfoLayoutEdPrefecture.error =
                if (provideInfoEdPrefecture.text.trim().toString().isEmpty()) errorMsg else null
        }
    }

    private fun RadioGroup.getUserGender(): String {
        return when (this.checkedRadioButtonId) {
            R.id.provide_info_radio_bt_male -> "Nam"
            R.id.provide_info_radio_bt_female -> "Nữ"
            else -> ""
        }
    }
}