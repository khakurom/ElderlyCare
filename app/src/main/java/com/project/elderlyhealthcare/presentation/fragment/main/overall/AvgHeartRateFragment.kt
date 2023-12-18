package com.project.elderlyhealthcare.presentation.fragment.main.overall

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAvgHeartRateBinding
import com.project.elderlyhealthcare.domain.models.AvgHeartRateModel
import com.project.elderlyhealthcare.presentation.adapter.AvgHeartRateAdapter
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.MonthYearPickerDialog
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class AvgHeartRateFragment : BaseFragment<OverallViewModel, FragmentAvgHeartRateBinding>(R.layout.fragment_avg_heart_rate) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    override fun variableId(): Int = BR.avgHeartRateViewModel

    override fun createViewModel(): Lazy<OverallViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAvgHeartRateBinding {
        return FragmentAvgHeartRateBinding.bind(view)
    }

    override fun init() {
        super.init()
        setCurrentTime()
        getHearRate()
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

    private fun getHearRate() {
        viewModel?.getHeartRate(getCurrentTime())
        viewModel?.listHeartRate?.observe(this) { heartRateList ->
            heartRateList?.let {
                var totalHeartRate = 0
                for (i in it) {
                    totalHeartRate += i
                }
                val avgHeartRate = (totalHeartRate / it.size).toFloat()
                checkNetworkIsAvailable(avgHeartRate)
            }
        }
    }

    private fun checkNetworkIsAvailable(avgHeartRate: Float) {
        if (Utils.isNetworkAvailable(requireContext())) {
            updateAvgHeartRate(avgHeartRate)
        } else {
            Utils.showDialog(requireContext(), "Vui lòng kết nối internet")
        }
    }

    private fun updateAvgHeartRate(avgHeartRate: Float) {
        binding.progressBar.visibility = View.VISIBLE
        val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
        databaseReference.child("data").child(dataKey).child(getString(R.string.key_health_param)).child("AvgHeartRate")
            .child(getCurrentTime().replace("/", ""))
            .setValue(AvgHeartRateModel(avgHeartRate, getCurrentTime()))
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                getListAvgHeartRate ()
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
            }
    }

    private fun setCurrentTime() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        binding.avgHeartRateTvDate.text = getString(R.string.month_year, (month + 1).toString(), year.toString())
    }

    private fun selectDate() {
        MonthYearPickerDialog().apply {
            setListener { _, month, year, _ ->
                binding.avgHeartRateTvDate.text = getString(R.string.month_year, (month + 1).toString(), year.toString())

            }
            show(this@AvgHeartRateFragment.parentFragmentManager, "MonthYearPickerDialog")
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

    private fun getListAvgHeartRate () {
        binding.progressBar.visibility = View.VISIBLE
        val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
        val databaseReference = FirebaseDatabase.getInstance().getReference("data")
            .child(dataKey)
            .child(getString(R.string.key_health_param))
            .child("AvgHeartRate")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val avgHeartRates: MutableList<AvgHeartRateModel> = mutableListOf()
                for (dataSnapshot in snapshot.children) {
                    val avgHeartRateModel = dataSnapshot.getValue(AvgHeartRateModel::class.java)
                    avgHeartRateModel?.let {
                        avgHeartRates.add(it)
                    }
                }
                binding.progressBar.visibility = View.GONE
                setRecycleView (avgHeartRates)
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun setRecycleView (avgHeartRateList : MutableList<AvgHeartRateModel>) {
        val adapter : AvgHeartRateAdapter by lazy {
            AvgHeartRateAdapter()
        }
        adapter.submitList(avgHeartRateList)
        binding.avgHeartRateRcv.adapter  = adapter
    }

}