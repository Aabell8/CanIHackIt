package com.austinabell8.canihackit.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.austinabell8.canihackit.R
import com.austinabell8.canihackit.model.ResultItem
import com.austinabell8.canihackit.utils.SearchListListener

class SearchRecyclerAdapter(context: Context, results: List<ResultItem>,
                            private val itemListener: SearchListListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext : Context = context
    private val mResults : List<ResultItem> = results

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_result, parent, false)
        return SearchViewHolder(mView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val r = mResults[position]
        val rHolder = holder as SearchViewHolder

        //Update data in SearchViewHolder
        rHolder.name.text = r.name
        rHolder.location.text = r.location
        rHolder.description.text = r.description
        rHolder.regularLayout.setOnClickListener { v -> itemListener.recyclerViewListClicked(v, rHolder.layoutPosition) }

        val layoutManager = LinearLayoutManager(mContext)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rHolder.tagsRecyclerView.layoutManager = layoutManager
        rHolder.tagsRecyclerView.isNestedScrollingEnabled = false

        if(r.tags!=null){
            val mSearchAdapter = TagRecyclerAdapter(r.tags)
            rHolder.tagsRecyclerView.adapter = mSearchAdapter
        }
    }

    override fun getItemCount(): Int {
        return mResults.size
    }

    private inner class SearchViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var regularLayout: CardView = view.findViewById(R.id.result_card_view)
        internal var profilePic: ImageView = view.findViewById(R.id.iv_result_item)
        internal var name: TextView = view.findViewById(R.id.result_name)
        internal var location: TextView = view.findViewById(R.id.result_location)
        internal var description: TextView = view.findViewById(R.id.result_description)
        internal var tagsRecyclerView: RecyclerView = view.findViewById(R.id.lv_result_tags)
    }

}