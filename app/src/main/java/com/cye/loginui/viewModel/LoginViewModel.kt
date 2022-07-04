package com.cye.loginui.viewModel

import androidx.lifecycle.MutableLiveData
import com.cye.loginui.WebService.getApi
import com.cye.loginui.model.ApiDataModel
import com.cye.loginui.model.UserModel
import com.google.gson.Gson

class LoginViewModel : BaseViewModel() {

    val liveUserModel = MutableLiveData<UserModel>()
    val liveApiData = MutableLiveData<ApiDataModel>()

    fun userLogin(userModel: UserModel) {
        getResponse(getApi()::userLogin, userModel) {
            if (it.data != null && it.data != "")
                liveUserModel.value = Gson().fromJson(it.data.toString(), UserModel::class.java)
            liveApiData.value = it
        }
    }

    fun userRegister(userModel: UserModel) {
        getResponse(getApi()::userRegister, userModel) {
            liveApiData.value = it
        }
    }
}