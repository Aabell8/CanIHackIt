package com.austinabell8.canihackit.activities

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.support.v4.util.Pair
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.austinabell8.canihackit.R
import com.austinabell8.canihackit.adapters.SearchRecyclerAdapter
import com.austinabell8.canihackit.model.ResultItem
import com.austinabell8.canihackit.utils.Constants
import com.austinabell8.canihackit.utils.SearchListListener
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    private lateinit var mName: String
    private lateinit var mDescription: String
    private val mResults: MutableList<ResultItem> = mutableListOf()

    private lateinit var mResultsRecyclerView: RecyclerView
    private lateinit var mSpinner: ProgressBar
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mSearchAdapter: SearchRecyclerAdapter
    private lateinit var mRecyclerViewListener: SearchListListener

    private lateinit var mAsyncTask: RetrieveResultsAsync

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
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun recyclerViewListClicked(v: View, position: Int) {
                val transitionIntent = newIntent(this@SearchActivity, position)
                val placeImage = v.findViewById<ImageView>(R.id.iv_result_item)
                val placeName = v.findViewById<TextView>(R.id.result_name)
                val statusBar = findViewById<View>(android.R.id.statusBarBackground)

                val imagePair = Pair.create(placeImage as View, "tImage")
                val namePair = Pair.create(placeName as View, "tName")

                val statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)

                val pairs = mutableListOf(imagePair, namePair, statusPair)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SearchActivity,
                        *pairs.toTypedArray())
                ActivityCompat.startActivity(this@SearchActivity, transitionIntent, options.toBundle())
            }
        }

        mAsyncTask = RetrieveResultsAsync()
        mAsyncTask.execute()

        //TODO: Get all results
//        mResults.add(ResultItem("One App Idea", "Google Play Store", "test description",
//                mutableListOf("test1", "test2", "Another tag", "So many tags wow", "Last one")))
        mResults.add(ResultItem("Another App Idea", "Github", "This app definitely exists",
                mutableListOf("Machine Learning")))

        initRecyclerView()
    }

    private fun newIntent(context: Context, position: Int): Intent {
        val intent = Intent(context, ResultDetailsActivity::class.java)
        intent.putExtra(Constants.INTENT_RESULT, mResults[position])
        return intent
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Search Results"
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mResultsRecyclerView.layoutManager = mLayoutManager

        mSearchAdapter = SearchRecyclerAdapter(applicationContext, mResults, mRecyclerViewListener)
        mResultsRecyclerView.adapter = mSearchAdapter
        if (mResults.size>0) {
            mSpinner.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Cancel background thread task when activity is destroyed
        mAsyncTask.cancel(true)
    }

    /**
     * This Async task calls the products API Call and retrieves necessary fields
     */
    private inner class RetrieveResultsAsync : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg strings: String): String? {
            val client = OkHttpClient()
            val reqURL = "Notaurl" //TODO: Get actual url

            val request: Request
            request = Request.Builder()
                    .url("$reqURL")
                    .build()

            var response: Response? = null

            //GET request call
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //GET request return body, return json string
            try {
                if (response != null) {
                    return response.body()?.string()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            if (s == null) {
                //If response back was invalid or failed, display toast
                Toast.makeText(mResultsRecyclerView.context, "Could not retrieve data, try again", Toast.LENGTH_SHORT).show()
            } else {
                //Build product list from json if successful
                val gson = Gson()
//                val test = gson.fromJson(s, ProductsJSON::class.java)
//                mProducts = test.getProducts()
                initRecyclerView()
            }
        }
    }
}
