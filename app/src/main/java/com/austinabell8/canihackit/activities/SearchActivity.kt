package com.austinabell8.canihackit.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.austinabell8.canihackit.R
import com.austinabell8.canihackit.utils.Constants

class SearchActivity : AppCompatActivity() {

    private lateinit var mName : String
    private lateinit var mDescription : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val bundle = intent.extras
        if (bundle!=null){
            mName = bundle.getString(Constants.INTENT_NAME)
            mDescription = bundle.getString(Constants.INTENT_DESCRIPTION)
        }
    }
}
