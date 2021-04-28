package com.keysoc.core.model.http.response


/**
 * Created by KennethTse on 14/11/2017.
 */

open class ListObjRespond<T> : BaseRespond {

    var listObj: List<T>? = null

    override val isSuccess: Boolean
        get() = super.isSuccess && listObj != null

    constructor() {
        status = 0
        listObj = null
    }


    constructor(status: Int, listObj: List<T>) {
        this.status = status
        this.listObj = listObj
    }
}
