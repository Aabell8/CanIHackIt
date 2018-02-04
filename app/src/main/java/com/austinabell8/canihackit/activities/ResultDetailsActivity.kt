package com.austinabell8.canihackit.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.austinabell8.canihackit.R
import com.austinabell8.canihackit.model.ResultItem
import com.austinabell8.canihackit.utils.Constants

class ResultDetailsActivity : AppCompatActivity() {

    private lateinit var resultItem: ResultItem
    private lateinit var mNameTxt: TextView
    private lateinit var mDescriptionTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_details)

        mNameTxt = findViewById(R.id.tv_name)
        mDescriptionTxt = findViewById(R.id.tv_description)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initActionBar()

        val bundle = intent.extras
        if (bundle != null) {
            resultItem = bundle.getParcelable(Constants.INTENT_RESULT)
            mNameTxt.text = resultItem.name
            mDescriptionTxt.text = resultItem.description
        }
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Details"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return false
    }
}
