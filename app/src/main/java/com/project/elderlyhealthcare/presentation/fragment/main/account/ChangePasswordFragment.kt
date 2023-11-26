package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.view.View
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentChangePasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<AccountViewModel, FragmentChangePasswordBinding>(R.layout.fragment_change_password) {
	override fun variableId(): Int = BR.changePwViewModel

	override fun createViewModel(): Lazy<AccountViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentChangePasswordBinding {
		return FragmentChangePasswordBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			changePwFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
		}
	}

}