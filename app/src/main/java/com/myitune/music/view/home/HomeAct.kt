package com.myitune.music.view.home

import android.os.Bundle
import android.util.Log
import com.myitune.core.model.http.SimpleError
import com.myitune.core.model.http.SimpleRequestListener
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.music.databinding.ActivityHomeBasicBinding
import com.myitune.music.model.http.request.GetSearchRequest
import com.myitune.music.model.http.respond.SearchRespond

class HomeAct : BaseActivity<ActivityHomeBasicBinding>() {

    private val TAG = "HomeAct"

    private lateinit var albumDataManager: AlbumDataManager

    private var tabSearchLayoutMan: TabSearchLayoutMan? = null
    private var tabBookmarkLayoutMan: TabBookmarkLayoutMan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterFromBottomAnimation()

        setupActionBar()
        setupBodyUI()

        getITunesData()
    }

    override fun setupBodyUI() {

        albumDataManager = AlbumDataManager(this)

        tabSearchLayoutMan = TabSearchLayoutMan(this, viewBinding, albumDataManager)
        tabSearchLayoutMan?.setupBodyUI()

        tabBookmarkLayoutMan = TabBookmarkLayoutMan(this, viewBinding, albumDataManager)
        tabBookmarkLayoutMan?.setupBodyUI()
        tabBookmarkLayoutMan?.updateDataIntoUI()

        // Tab Button setup
        viewBinding.btnTabSearch.setOnClickListener {
            Log.i(TAG, "Clicked1")
            tabBookmarkLayoutMan?.hide()
            tabSearchLayoutMan?.show()
        }

        viewBinding.btnTabBookMark.setOnClickListener {
            Log.i(TAG, "Clicked2")
            tabBookmarkLayoutMan?.show()
            tabSearchLayoutMan?.hide()
        }
    }

    private fun getITunesData() {

        startLoading()

        GetSearchRequest(
            this,
            object : SimpleRequestListener<SearchRespond> {
                override fun onError(response: SearchRespond?) {
                    stopLoadingAction()
                    SimpleError.show(this@HomeAct)
                }

                override fun onSuccess(response: SearchRespond?) {
                    stopLoadingAction()

                    /* change to listen pattern */
                        updateDataIntoUI()
                }
            }
        ).execute()


    }


    private fun updateDataIntoUI() {

        tabSearchLayoutMan?.updateDataIntoUI()
        tabBookmarkLayoutMan?.updateDataIntoUI()
    }

    override fun onResume() {
        super.onResume()

        tabSearchLayoutMan?.onResume()
        tabBookmarkLayoutMan?.onResume()
    }

    override fun onPause() {
        tabSearchLayoutMan?.onPause()
        tabBookmarkLayoutMan?.onPause()

        super.onPause()
    }

}