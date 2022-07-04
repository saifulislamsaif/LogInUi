package com.cye.loginui.view.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cye.loginui.R
import com.cye.loginui.databinding.ActivityLogInBinding
import com.cye.loginui.model.UserModel
import com.cye.loginui.utils.*
import com.cye.loginui.viewModel.LoginViewModel
import com.google.gson.Gson

class LogInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activity: Activity

    private var userModel: UserModel? = null

    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        activity = this
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.cvLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
    }

    private fun observeViewModel() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.application = activity.application

        viewModel.liveApiData.observe(this) {
            Log.d("010101", "\n\n\n" + Gson().toJson(it))
            if (it.status == Constant.S_SUCCESS) {
                Log.d("010101", "\n\n\n" + Gson().toJson(it.data))
                //gotoHomePage()
            } else
                activity.popupAlert(it.status.toString(), it.message.toString())
        }
    }

    override fun onClick(v: View?) {
        closeKeyboard()
        when (v?.id) {
            R.id.cvLogin -> checkLogin()
            R.id.tvSignUp -> gotoSignup()
        }
    }

    private fun checkLogin() {

        val email = binding.etEmail.text.toString().trim()
        var password = binding.etPassword.text.toString().trim()

        if (email.length < 3) {
            binding.etEmail.error = "Enter valid email."
            binding.etEmail.resources
        } else if (password.length < 3) {
            binding.etPassword.error = "Enter valid email."
            binding.etPassword.resources
        } else if (!haveNetwork()) {
            popupNoInternet()
        } else {
            password = encode(password)
            userModel = UserModel()
            userModel?.email = email
            userModel?.password = password

            viewModel.userLogin(userModel!!)
        }

    }

    private fun gotoSignup() {

    }
}