package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MedicineEventFragment :
    BaseFragment<EventViewModel, FragmentMedicineEventBinding>(R.layout.fragment_medicine_event) {

    private val medicineAdapter = MedicineAdapter()


    override fun variableId(): Int = BR.medicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentMedicineEventBinding {
        return FragmentMedicineEventBinding.bind(view)
    }

    override fun init() {
        super.init()
        medicineAdapter.apply {
            onItemSelectListener = object : OnItemSelectListener<MedicineEventModel> {
                override fun onItemSelected(item: MedicineEventModel, position: Int) {
                    findNavController().navigate(
                        MedicineEventFragmentDirections.actionMedicineEventFragmentToUpdateMedicineEventFragment(
                            item
                        )
                    )
                }
            }
            onItemRemoveListener = object : OnItemRemoveListener<MedicineEventModel> {
                override fun onItemRemove(item: MedicineEventModel, position: Int) {
                    viewModel?.deleteMedicineEvent(item.id)
                }
            }
        }


        viewModel?.getMedicineEvent()
        binding.apply {
            medicineFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            medicineFabAdd.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(MedicineEventFragmentDirections.actionMedicineEventFragmentToAddMedicineFragment())
                }
            })

            medicineRcvListMedicine.adapter = medicineAdapter

        }
    }


}