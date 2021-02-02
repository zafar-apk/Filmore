package com.kangaroo.filmore.Views.ui.details

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Utils.Constants

class DetailsHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_home)

        val textView = findViewById<TextView>(R.id.textView_home_details)

        textView.text = intent.getIntExtra(Constants.DATA_POSITION, 0).toString()
    }
}