package com.cye.loginui.model

import com.cye.loginui.utils.Constant.S_FAILED
import com.cye.loginui.utils.Constant.S_MESSAGE

class ApiDataModel {
    var status: String? = S_FAILED
    var message: String? = S_MESSAGE
    var totalItem: Long? = 0
    var data: Any? = null
}