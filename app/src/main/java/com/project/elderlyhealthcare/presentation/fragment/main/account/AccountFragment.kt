package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAccountBinding
import com.project.elderlyhealthcare.presentation.activity.NotLoginActivity
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>(R.layout.fragment_account) {
	override fun variableId(): Int = BR.accountViewModel

	override fun createViewModel(): Lazy<AccountViewModel> = viewModels()

	override fun bindView(view: View): FragmentAccountBinding {
		return FragmentAccountBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			accountLayoutLogout.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					startActivity(Intent (requireContext(), NotLoginActivity :: class.java))
					activity?.finish()
				}
			})
			accountLayoutInfo.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToProfileFragment())
				}
			})

			accountLayoutChangePw.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToChangePasswordFragment())
				}
			})
		}
	}

}