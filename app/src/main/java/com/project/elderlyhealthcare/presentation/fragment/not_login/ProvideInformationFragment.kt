package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentProvideInformationBinding
import com.project.elderlyhealthcare.databinding.FragmentSignUpBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProvideInformationFragment : BaseFragment<NotLoginViewModel, FragmentProvideInformationBinding>(R.layout.fragment_provide_information) {
	override fun variableId(): Int = BR.provideInfoViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentProvideInformationBinding {
		return FragmentProvideInformationBinding.bind(view)
	}


	override fun init() {
		super.init()
		binding.apply {
			provideInfoFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			provideInfoBtContinue.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(ProvideInformationFragmentDirections.actionProvideInformationFragmentToVerifyPhoneNumberFragment())
				}
			})
			val adapterItems = ArrayAdapter (requireContext(), R.layout.item_prefecture, Constant.listPrefecture )
			autoCompleteText.setAdapter(adapterItems)
		}
	}
}