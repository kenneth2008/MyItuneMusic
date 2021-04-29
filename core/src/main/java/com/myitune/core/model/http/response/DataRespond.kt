package com.myitune.core.model.http.response

/**
 * Created by KennethTse on 14/11/2017.
 */

open class DataRespond<T> : BaseRespond {

    var data: T? = null

    override val isSuccess: Boolean
        get() = super.isSuccess

    constructor() {
        status = 0
        data = null
    }


    constructor(status: Int, data: T) {
        this.status = status
        this.data = data
    }
}
