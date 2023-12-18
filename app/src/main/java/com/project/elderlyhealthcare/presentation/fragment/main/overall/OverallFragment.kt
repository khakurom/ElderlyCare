package com.project.elderlyhealthcare.presentation.fragment.main.overall

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.HeartRateEntity
import com.project.elderlyhealthcare.databinding.FragmentOverallBinding
import com.project.elderlyhealthcare.domain.models.HealthParam
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class OverallFragment : BaseFragment<OverallViewModel, FragmentOverallBinding>(R.layout.fragment_overall) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun variableId(): Int = BR.overallViewModel

    override fun createViewModel(): Lazy<OverallViewModel> = viewModels()

    override fun bindView(view: View): FragmentOverallBinding {
        return FragmentOverallBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            layoutAvgHeartRate.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(OverallFragmentDirections.actionOverallFragmentToAvgHeartRateFragment())
                }
            })
            layoutAvgOxygen.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(OverallFragmentDirections.actionOverallFragmentToAvgOxygenFragment())
                }
            })

            with(overallFrRefresh) {
                setOnRefreshListener {
                    isRefreshing = true
                    checkNetworkIsAvailable()
                    isRefreshing = false
                }
            }
        }
        checkNetworkIsAvailable()
    }

    private fun checkNetworkIsAvailable() {
        binding.apply {
            if (Utils.isNetworkAvailable(requireContext())) {
                getWeightParam()
                getHealthParam()
            } else {
                Utils.showDialog(requireContext(), "Vui lòng kết nối internet")
            }
        }
    }

    private fun getHealthParam() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            val phoneNumber = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
            val dataNodeReference = databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_health_param))

            dataNodeReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val healthParam = dataSnapshot.getValue(HealthParam::class.java)
                        overallTvHeartRate.text = healthParam?.heartRate.toString()
                        viewModel?.insertHeartRate(HeartRateEntity(
                            heartRate = healthParam?.heartRate,
                            day = getCurrentTime()
                        ))
                        overallTvOxygen.text = healthParam?.oxyGen.toString()
                    } else {
                        overallTvOxygen.text = ""
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Utils.showDialog(requireContext(), "Hệ thống lỗi, vui lòng thử lại")
                    progressBar.visibility = View.GONE
                }
            })
        }

    }

    private fun getWeightParam() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            val phoneNumber = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
            val dataNodeReference =
                databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_customer_info))
            dataNodeReference.orderByKey().equalTo("elderWeight")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.hasChild("elderWeight")) {
                            val elderWeight = dataSnapshot.child("elderWeight").value
                            overallTvWeight.text = elderWeight.toString()
                        } else {
                            overallTvWeight.text = ""
                        }
                        progressBar.visibility = View.GONE
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Utils.showDialog(requireContext(), "Hệ thống lỗi, vui lòng thử lại")
                        progressBar.visibility = View.GONE
                    }
                })
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


}