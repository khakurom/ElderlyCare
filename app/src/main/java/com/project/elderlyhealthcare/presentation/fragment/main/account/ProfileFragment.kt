package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentProfileBinding
import com.project.elderlyhealthcare.domain.models.CustomerInfoModel
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.calculateAge
import com.project.elderlyhealthcare.utils.Utils.isNetworkAvailable
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :  BaseFragment<AccountViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {

	private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

	private var listener: OnFragmentInteractionListener? = null
	override fun variableId(): Int = BR.profileViewModel

	override fun createViewModel(): Lazy<AccountViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentProfileBinding {
		return FragmentProfileBinding.bind(view)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context is OnFragmentInteractionListener) {
			listener = context
		}
	}

	override fun init() {
		super.init()
		checkNetworkIsAvailable ()
		binding.apply {
			profileFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
		}
		listener?.updateBottomNavVisible(true)
	}

	private fun checkNetworkIsAvailable () {
		if (isNetworkAvailable(requireContext())) {
			getUserInfo ()
		} else {
			showDialog(requireContext(), "Vui lòng kết nối internet")
		}
	}


	private fun getCustomerInfo (callback: (CustomerInfoModel?) -> Unit) {
		binding.progressBar.visibility = View.VISIBLE
		val phoneNumber = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
		val dataNodeReference = databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_customer_info))

		dataNodeReference.addListenerForSingleValueEvent(object : ValueEventListener {
			override fun onDataChange(dataSnapshot: DataSnapshot) {
				if (dataSnapshot.exists()) {
					val customerInfoModel = dataSnapshot.getValue(CustomerInfoModel::class.java)
					callback(customerInfoModel)
				} else {
					callback(null)
				}
				binding.progressBar.visibility = View.GONE
			}

			override fun onCancelled(databaseError: DatabaseError) {
				binding.progressBar.visibility = View.GONE
				callback(null)
			}
		})
	}

	private fun getUserInfo () {
		getCustomerInfo {
			binding.customerInfoModel = it
			it?.elderDob?.let {elderDob ->
				binding.profileTvAgeElder.text = calculateAge(elderDob).toString()
			}
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		listener?.updateBottomNavVisible(false)
	}


}