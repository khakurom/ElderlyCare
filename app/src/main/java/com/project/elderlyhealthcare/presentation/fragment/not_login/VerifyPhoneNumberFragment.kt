package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentVerifyPhoneNumberBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class VerifyPhoneNumberFragment :
    BaseFragment<NotLoginViewModel, FragmentVerifyPhoneNumberBinding>(R.layout.fragment_verify_phone_number) {

    private val navArgs: VerifyPhoneNumberFragmentArgs by navArgs()

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false

    private lateinit var auth: FirebaseAuth
    private var verificationId: String = ""
    override fun variableId(): Int = BR.verifyPhoneViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentVerifyPhoneNumberBinding {
        return FragmentVerifyPhoneNumberBinding.bind(view)
    }

    override fun init() {
        super.init()
        setupOTPInputs()
        startPhoneNumberVerification ()
        createCountDownTimer()
        binding.apply {
            verifyFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            layoutVerifyPhoneNumber.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })

            verifyBtVerifyOtp.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    verifyCodeOtp()
                }
            })

            verifyTvClickHere.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (!isTimerRunning) {
                        reStartPhoneNumberVerification()
                        startCountdown()
                    }
                }
            })

            verifyTvEnterCodeOtp.text =
                getString(R.string.login_txt_enter_code_otp, navArgs.customerInfoModel.phoneNumber)
        }
    }

    private fun createCountDownTimer() {
        val initialTime = 60 * 1000 // 60 seconds in milliseconds
        val interval = 1000 // 1 second interval
        countDownTimer = object : CountDownTimer(initialTime.toLong(), interval.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val timeString = String.format("%02d:%02d", seconds / 60, seconds % 60)
                binding.verifyTvClickHere.text = timeString
            }

            override fun onFinish() {
                binding.verifyTvClickHere.text = getString(R.string.login_click_here)
                isTimerRunning = false
            }
        }
    }

    fun startCountdown() {
        if (!isTimerRunning) {
            countDownTimer.start()
            isTimerRunning = true
        }
    }


    private fun setupOTPInputs() {
        binding.apply {
            verifyInputCode1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        verifyInputCode2.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            verifyInputCode2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        verifyInputCode3.requestFocus()
                    } else {
                        verifyInputCode1.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            verifyInputCode3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        verifyInputCode4.requestFocus()
                    } else {
                        verifyInputCode2.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            verifyInputCode4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        verifyInputCode5.requestFocus()
                    } else {
                        verifyInputCode3.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            verifyInputCode5.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        verifyInputCode6.requestFocus()
                    } else {
                        verifyInputCode4.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            verifyInputCode6.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().trim().isNotEmpty()) {
                        view?.hideKeyboard()
                    } else {
                        verifyInputCode5.requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }
    }

    private fun verifyCodeOtp() {
        binding.apply {
            val digit1 = verifyInputCode1.text.trim().toString()
            val digit2 = verifyInputCode2.text.trim().toString()
            val digit3 = verifyInputCode3.text.trim().toString()
            val digit4 = verifyInputCode4.text.trim().toString()
            val digit5 = verifyInputCode5.text.trim().toString()
            val digit6 = verifyInputCode6.text.trim().toString()

            if (digit1.isEmpty() || digit2.isEmpty() || digit3.isEmpty() || digit4.isEmpty() || digit5.isEmpty() || digit6.isEmpty()) {
                showDialog(requireContext(), "Vui lòng nhập đầy đủ mã OTP")
            } else {
                val codeOtp = digit1 + digit2 + digit3 + digit4 + digit5 + digit6
                verifyPhoneNumberWithCode(codeOtp)
            }
        }
    }

    private fun startPhoneNumberVerification() {
        auth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+84${navArgs.customerInfoModel.phoneNumber}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun reStartPhoneNumberVerification() {
        auth = FirebaseAuth.getInstance()
        if (resendToken != null) {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+84${navArgs.customerInfoModel.phoneNumber}") // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(requireActivity()) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .setForceResendingToken(resendToken!!)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } else {
            startPhoneNumberVerification()
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // This method will be called when the verification is successfully sent to the user.
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }


        override fun onVerificationFailed(e: FirebaseException) {
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> showDialog(
                    requireContext(),
                    "Số điện thoại không hợp lệ. Vui lòng kiểm tra lại"
                )

                is FirebaseTooManyRequestsException -> showDialog(
                    requireContext(),
                    "Yêu cầu gửi mã OTP vượt quá số lần cho phép. Vui lòng lại!"
                )

                else -> showDialog(
                    requireContext(),
                    "Lỗi hệ thống. Vui lòng thử lại!"
                )
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            this@VerifyPhoneNumberFragment.verificationId = verificationId
            this@VerifyPhoneNumberFragment.resendToken = token
        }
    }

    private fun verifyPhoneNumberWithCode(code: String) {
        if (verificationId.isEmpty()) {
            showDialog(requireContext(), "Hệ thống bị lỗi. Vui lòng thử lại sau!")
        } else {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        binding.progressBar.visibility = View.VISIBLE
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    updateLoadCustomerInfo ()
                } else {
                    showDialog(requireContext(), "Mã OTP đã hết hạn hoặc không chính xác. Vui lòng nhập lại!")
                    binding.progressBar.visibility = View.GONE
                }
            }
    }

    private fun updateLoadCustomerInfo() {
        val newDataKey = navArgs.customerInfoModel.phoneNumber
        if (newDataKey != null) {
            databaseReference.child("data").child(newDataKey).setValue(navArgs.customerInfoModel)
                .addOnSuccessListener {
                    findNavController().navigate(VerifyPhoneNumberFragmentDirections.actionVerifyPhoneNumberFragmentToCompleteFragment())
                    binding.progressBar.visibility = View.GONE
                }
                .addOnFailureListener {
                    showDialog(requireContext(), it.toString())
                    binding.progressBar.visibility = View.GONE
                }

        }
    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }
}