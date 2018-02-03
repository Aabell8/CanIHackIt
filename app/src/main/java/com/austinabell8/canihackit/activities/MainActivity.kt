package com.austinabell8.canihackit.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.austinabell8.canihackit.R

import com.austinabell8.canihackit.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var mNameTxt : EditText
    private lateinit var mDescriptionText : EditText
    private lateinit var mSearchButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNameTxt = findViewById(R.id.input_app_name)
        mDescriptionText = findViewById(R.id.input_description)
        mSearchButton = findViewById(R.id.search_button)
        mSearchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(Constants.INTENT_NAME, mNameTxt.text.toString())
            intent.putExtra(Constants.INTENT_DESCRIPTION, mDescriptionText.text.toString())
            startActivity(intent)
        }

        mNameTxt.clearFocus()
    }
}
