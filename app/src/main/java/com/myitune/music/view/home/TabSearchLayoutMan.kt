package com.myitune.music.view.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.datamodel.database.AppDatabase
import com.myitune.datamodel.database.model.AlbumBookmark
import com.myitune.music.R
import com.myitune.music.databinding.ActivityHomeBasicBinding
import com.myitune.music.view.home.adapter.AlbumDataAdapter
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration
import java.util.Observer


class TabSearchLayoutMan(
    private val act: BaseActivity<*>,
    private val viewBinding: ActivityHomeBasicBinding,
    private val albumDataManager: AlbumDataManager
) {

    private val TAG = "TabSearchLayoutMan"

    private val albumDataAdapter = AlbumDataAdapter(act)
    private val db = AppDatabase.getDatabase(act.myApp)
    private var bookMarkDao = db.getAlbumBookmarkDao()

    fun setupBodyUI() {

        val lstData = viewBinding.llTabSearch.lstData

        lstData.layoutManager = LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false)



        lstData.addItemDecoration(
            DefaultItemDecoration(
                ContextCompat.getColor(
                    act,
                    R.color.gainsboro
                )
            )
        )

        lstData.setSwipeMenuCreator(mSwipeMenuCreator)
        lstData.setOnItemMenuClickListener(mItemMenuClickListener)

        lstData.adapter = albumDataAdapter
    }

    fun updateDataIntoUI() {

        val dataList = db.getAlbumDataDao().getAll()

        albumDataAdapter.setDataList(dataList)

    }

    fun show() {
        viewBinding.llTabSearch.swipeContainer.visibility = View.VISIBLE
    }

    fun hide() {
        viewBinding.llTabSearch.swipeContainer.visibility = View.GONE
    }

    /* Life Cycle */
    fun onResume() {
        albumDataManager.attach(albumDataObserver)
    }

    fun onPause() {
        albumDataManager.detach(albumDataObserver)
    }


    /* Bookmark Data Observer */
    private val albumDataObserver = Observer { _, _ ->
        Log.i(TAG, "myApp.propertyManager.propertyChanged()")

        // Change data only if it is not the first time, because initial will trigger once.
        // To Prevent trigger property change at onCreate, use countOfPropertyChange control it
        updateDataIntoUI()
    }

    /**
     * RecyclerView???Item??????Menu???????????????
     */
    private val mItemMenuClickListener =
        OnItemMenuClickListener { menuBridge, position ->
            menuBridge.closeMenu()

            val direction = menuBridge.direction // ???????????????????????????
            val menuPosition = menuBridge.position // ?????????RecyclerView???Item??????Position???

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                when (menuPosition) {
                    0 -> {
                        val data = albumDataAdapter.getItem(position)
                        val bookMarkRecord: AlbumBookmark? =
                            db.getAlbumBookmarkDao().getDataById(data.collectionId)


                        if (bookMarkRecord == null) {
                            // insert Bookmark
                            Log.i(TAG, "insert bookmark")

                            val newData = AlbumBookmark(data.collectionId)

                            bookMarkDao.insert(newData)
//                            albumDataAdapter.notifyItemChanged(position)
                            albumDataManager.add(newData)
                        } else {
                            // Delete bookmark
                            Log.i(TAG, "Delete bookmark")

                            bookMarkDao.deleteDataByID(data.collectionId)
//                            albumDataAdapter.notifyItemChanged(position)
                            albumDataManager.delete(data.collectionId)
                        }

                    }
                }
            }
        }


    /**
     * ?????????????????????Item?????????????????????????????????
     */
    private val mSwipeMenuCreator =
        SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, position ->

            val data = albumDataAdapter.getItem(position)
            val bookMarkRecord = db.getAlbumBookmarkDao().getDataById(data.collectionId)

            val width: Int = act.resources.getDimensionPixelSize(R.dimen.dp_70)

            // 1. MATCH_PARENT ???????????????????????????Item?????????;
            // 2. ???????????????????????????80;
            // 3. WRAP_CONTENT???????????????????????????;

            val closeItem: SwipeMenuItem =
                SwipeMenuItem(act).setBackground(ColorDrawable(Color.YELLOW))

            if (bookMarkRecord == null)
                closeItem.setImage(R.drawable.ic_bookmark_black)
            else
                closeItem.setImage(R.drawable.ic_bookmark_not_fill)


            closeItem.setWidth(width)
                .setHeight(act.resources.getDimensionPixelOffset(R.dimen.home_tab_search_swipe_menu_height))

            swipeRightMenu.addMenuItem(closeItem) // ????????????????????????
        }
}