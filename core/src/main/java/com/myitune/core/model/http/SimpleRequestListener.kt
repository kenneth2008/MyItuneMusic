package com.myitune.core.model.http


/**
 * Created by KennethTse on 21/12/2016.
 */

interface SimpleRequestListener<T> {

    // Error action
    fun onError(response: T?)

    // Success action
    fun onSuccess(response: T?)

}
