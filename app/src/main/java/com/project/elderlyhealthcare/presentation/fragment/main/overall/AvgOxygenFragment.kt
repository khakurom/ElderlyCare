package com.project.elderlyhealthcare.presentation.fragment.main.overall

import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAvgOxygenBinding
import com.project.elderlyhealthcare.domain.models.AvgOxygenModel
import com.project.elderlyhealthcare.presentation.adapter.AvgOxygenAdapter
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.MonthYearPickerDialog
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AvgOxygenFragment : BaseFragment<OverallViewModel, FragmentAvgOxygenBinding>(R.layout.fragment_avg_oxygen)  {

	private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

	override fun variableId(): Int = BR.avgOxygenViewModel

	override fun createViewModel(): Lazy<OverallViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentAvgOxygenBinding {
		return FragmentAvgOxygenBinding.bind(view)
	}

	override fun init() {
		super.init()
		setCurrentTime()
		getOxygen()
		binding.apply {
			avgOxygenFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			avgOxygenLayoutDate.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					selectDate()
				}
			})
		}
	}

	private fun getOxygen() {
		viewModel?.getOxygen(getCurrentTime())
		viewModel?.listOxygen?.observe(this) { oxygenList ->
			oxygenList?.let {
				var totalOxygen = 0
				for (i in it) {
					totalOxygen += i
				}
				val avgOxygen = (totalOxygen / it.size).toFloat()
				checkNetworkIsAvailable(avgOxygen)
			}
		}
	}

	private fun checkNetworkIsAvailable(avgOxygen: Float) {
		if (Utils.isNetworkAvailable(requireContext())) {
			updateAvgHeartRate(avgOxygen)
		} else {
			Utils.showDialog(requireContext(), "Vui lòng kết nối internet")
		}
	}

	private fun updateAvgHeartRate(avgOxygen: Float) {
		binding.progressBar.visibility = View.VISIBLE
		val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
		databaseReference.child("data").child(dataKey).child(getString(R.string.key_health_param)).child("AvgOxygen")
			.child(getCurrentTime().replace("/", ""))
			.setValue(AvgOxygenModel(avgOxygen, getCurrentTime()))
			.addOnSuccessListener {
				binding.progressBar.visibility = View.GONE
				getListAvgOxygen ()
			}
			.addOnFailureListener {
				binding.progressBar.visibility = View.GONE
			}
	}

	private fun getCurrentTime(): String {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val day = calendar.get(Calendar.DATE)
		return getString(
			R.string.day_month_year,
			day.toString(),
			(month + 1).toString(),
			year.toString()
		)
	}

	private fun getListAvgOxygen () {
		binding.progressBar.visibility = View.VISIBLE
		val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
		val databaseReference = FirebaseDatabase.getInstance().getReference("data")
			.child(dataKey)
			.child(getString(R.string.key_health_param))
			.child("AvgOxygen")
		databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val avgOxygen: MutableList<AvgOxygenModel> = mutableListOf()
				for (dataSnapshot in snapshot.children) {
					val avgOxygenModel = dataSnapshot.getValue(AvgOxygenModel::class.java)
					avgOxygenModel?.let {
						avgOxygen.add(it)
					}
				}
				binding.progressBar.visibility = View.GONE
				setRecycleView (avgOxygen)
			}

			override fun onCancelled(error: DatabaseError) {
				binding.progressBar.visibility = View.GONE
			}
		})
	}
	private fun setRecycleView (avgOxygenList : MutableList<AvgOxygenModel>) {
		val adapter : AvgOxygenAdapter by lazy {
			AvgOxygenAdapter()
		}
		adapter.submitList(filterListByMonthAndYear(avgOxygenList))
		binding.avgOxygenRcv.adapter  = adapter
	}

	private fun filterListByMonthAndYear(list: List<AvgOxygenModel>): List<AvgOxygenModel> {
		val (targetMonth, targetYear) = Utils.extractMonthAndYear(binding.avgOxygenTvDate.text.toString())
		return list.filter { obj ->
			val parts = obj.day?.split("/")
			val objMonth = parts?.getOrNull(1)
			val objYear = parts?.getOrNull(2)

			objMonth == targetMonth && objYear == targetYear
		}
	}


	private fun setCurrentTime() {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		binding.avgOxygenTvDate.text = getString(R.string.month_year, (month+1).toString(), year.toString())

	}

	private fun selectDate() {
		MonthYearPickerDialog().apply {
			setListener { _, month, year, _ ->
				binding.avgOxygenTvDate.text = getString(R.string.month_year, (month+1).toString(), year.toString())
				getListAvgOxygen()
			}
			show(this@AvgOxygenFragment.parentFragmentManager, "MonthYearPickerDialog")
		}
	}

}