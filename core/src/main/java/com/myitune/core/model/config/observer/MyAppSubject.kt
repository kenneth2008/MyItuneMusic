package com.myitune.core.model.config.observer

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList


abstract class MyAppSubject<T : Observable> {

    // 保存注册的观察者对象
    private val mObservers = ArrayList<Observer>()

    //注册观察者对象
    fun attach(observer: Observer) {
        mObservers.add(observer)
        println("Attached an observer")
    }


    //注销观察者对象
    fun detach(observer: Observer) {
        mObservers.remove(observer)
    }

    fun cleanObserver(){
        mObservers.clear()
    }

    // 通知所有注册的观察者对象
    fun notifyObservers(data: T?) {
        Log.i("MyAppSubject", "notifyObservers(), mObservers.size:${mObservers.size}")
        for (observer in mObservers) {
            observer.update(data, null)
        }
    }


}