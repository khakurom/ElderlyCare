package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.view.View
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAccountBinding
import com.project.elderlyhealthcare.databinding.FragmentProfileBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :  BaseFragment<AccountViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {
	override fun variableId(): Int = BR.profileViewModel

	override fun createViewModel(): Lazy<AccountViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentProfileBinding {
		return FragmentProfileBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			profileFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
		}
	}


}