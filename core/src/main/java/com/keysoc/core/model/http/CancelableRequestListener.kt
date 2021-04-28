package com.keysoc.core.model.http


/**
 * Created by KennethTse on 21/12/2016.
 */

interface CancelableRequestListener<T> {

    fun onCancel()

    // Error action
    fun onError(respond: T?)

    // Success action
    fun onSuccess(respond: T?)

}
