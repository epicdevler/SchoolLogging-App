package com.cedarsstudio.internal.schoollogging.remote.utils

sealed class Response<D> (val data : D? = null, val msg: String? = null){
    class Success<D>(data : D? = null, msg: String? = null) : Response<D>(data, msg)
    class Error<D>(msg: String? = null) : Response<D>(null, msg)
}

