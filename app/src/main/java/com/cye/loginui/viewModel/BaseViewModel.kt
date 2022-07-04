package com.cye.loginui.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.cye.loginui.BuildConfig
import com.cye.loginui.WebService.NetworkError
import com.cye.loginui.utils.Constant
import com.cye.loginui.utils.popupAlert
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<String>()

    lateinit var application: Application

    //multipleParameter
    fun <T : Any, U : Any> getResponse(
        requesterMethod: suspend (T) -> U,
        body: T,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod(body))
            } catch (errorMsg: Throwable) {
                printApiResponse(errorMsg.localizedMessage)
                networkError.value = NetworkError.getServerResponseMessage(errorMsg, application)
                printApiResponse(networkError.value.toString())

                Toast.makeText(
                    application.applicationContext,
                    networkError.value.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }
            isLoading.value = false
        }
    }

    //singleParameter
    fun <U : Any> getResponse(
        requesterMethod: suspend () -> U,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                onResponseMethod(requesterMethod())
            } catch (errorMsg: Throwable) {
                networkError.value = NetworkError.getServerResponseMessage(errorMsg, application)
                printApiResponse(errorMsg.localizedMessage)
            }
            isLoading.value = false
        }
    }

    private fun printApiResponse(errorMsg: String?) {
        if (BuildConfig.DEBUG)
            Log.d("010101", "\n\nApiResponse: $errorMsg")
    }

}