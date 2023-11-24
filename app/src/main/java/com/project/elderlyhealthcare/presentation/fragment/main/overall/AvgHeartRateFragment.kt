package com.project.elderlyhealthcare.presentation.fragment.main.overall

import android.view.View
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAvgHeartRateBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel
import com.project.elderlyhealthcare.utils.MonthYearPickerDialog
import com.project.elderlyhealthcare.utils.SingleClickListener
import java.util.Calendar


class AvgHeartRateFragment : BaseFragment<OverallViewModel, FragmentAvgHeartRateBinding>(R.layout.fragment_avg_heart_rate) {

	override fun variableId(): Int = BR.avgHeartRateViewModel

	override fun createViewModel(): Lazy<OverallViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentAvgHeartRateBinding {
		return FragmentAvgHeartRateBinding.bind(view)
	}

	override fun init() {
		super.init()
		getCurrentTime()
		binding.apply {
			avgHeartRateFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			avgHeartRateLayoutDate.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					selectDate()
				}
			})
		}
	}

	private fun getCurrentTime() {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		binding.avgHeartRateTvDate.text = getString(R.string.month_year, (month+1).toString(), year.toString())

	}

	private fun selectDate() {
		MonthYearPickerDialog().apply {
			setListener { _, month, year, _ ->
				binding.avgHeartRateTvDate.text = getString(R.string.month_year, (month+1).toString(), year.toString())

			}
			show(this@AvgHeartRateFragment.parentFragmentManager, "MonthYearPickerDialog")
		}
	}

}