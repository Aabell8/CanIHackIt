package com.austinabell8.canihackit.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import com.austinabell8.canihackit.R
import com.austinabell8.canihackit.adapters.SearchRecyclerAdapter
import com.austinabell8.canihackit.model.ResultItem
import com.austinabell8.canihackit.utils.Constants
import com.austinabell8.canihackit.utils.SearchListListener

class SearchActivity : AppCompatActivity() {

    private lateinit var mName: String
    private lateinit var mDescription: String
    private val mResults: MutableList<ResultItem> = mutableListOf()

    private lateinit var mResultsRecyclerView: RecyclerView
    private lateinit var mSpinner: ProgressBar
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mSearchAdapter: SearchRecyclerAdapter
    private lateinit var mRecyclerViewListener: SearchListListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mResultsRecyclerView = findViewById(R.id.rvResults)
        mSpinner = findViewById(R.id.progressBarSearch)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initActionBar()

        val bundle = intent.extras
        if (bundle != null) {
            mName = bundle.getString(Constants.INTENT_NAME)
            mDescription = bundle.getString(Constants.INTENT_DESCRIPTION)
        }

        mRecyclerViewListener = object : SearchListListener {
            override fun recyclerViewListClicked(v: View, position: Int) {
                //TODO: Click listener for recycler view
            }
        }

        mResults.add(ResultItem("One App Idea", "Google Play Store", "test description",
                mutableListOf("test1", "test2", "Another tag", "So many tags wow", "Last one")))
        mResults.add(ResultItem("Another App Idea", "Github", "This app definitely exists",
                mutableListOf("Machine Learning")))

        initRecyclerView()
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
//            actionBar.setDisplayShowTitleEnabled(true)
        actionBar?.title = "Search Results"
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mResultsRecyclerView.layoutManager = mLayoutManager

        mSearchAdapter = SearchRecyclerAdapter(applicationContext, mResults, mRecyclerViewListener)
        mResultsRecyclerView.adapter = mSearchAdapter
    }
}
