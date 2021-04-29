package com.myitune.music.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.datamodel.database.AppDatabase
import com.myitune.datamodel.database.model.AlbumBookmark
import com.myitune.datamodel.database.model.AlbumData
import com.myitune.music.R
import com.myitune.music.databinding.ItemAlbumListBinding

class AlbumDataAdapter(
    private val act: BaseActivity<*>
) : RecyclerView.Adapter<AlbumDataAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(act)
    private var dataList: List<AlbumData> = ArrayList()

    private val db = AppDatabase.getDatabase(act.myApp)

    inner class ViewHolder(
        private val viewBinder: ItemAlbumListBinding
    ) : RecyclerView.ViewHolder(viewBinder.root) {

        fun onBind(data: AlbumData) {

            Glide.with(act.myApp)
                .load(data.artworkUrl60)
                .apply {
                    this.fitCenter()
                        .error(Glide.with(act.myApp)
                            .load(R.drawable.place_holder_copy)
                            .apply {
                                this.centerCrop()
                            })
                }.into(viewBinder.imgIcon)

            viewBinder.lblTitle.text = data.collectionName
            viewBinder.lblArtise.text = data.artistName

            val strPrice = "${data.currency} ${data.collectionPrice}"
            viewBinder.lblPrice.text = strPrice

            val bookMarkRecord: AlbumBookmark? =
                db.getAlbumBookmarkDao().getDataById(data.collectionId)

            if (bookMarkRecord == null)
                viewBinder.imgBookMark.visibility = View.GONE
            else
                viewBinder.imgBookMark.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemAlbumListBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.onBind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getItem(position: Int): AlbumData {
        return dataList[position]
    }

    fun setDataList(dataList: List<AlbumData>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}